scope有五个值

1. singleton，单例，默认
2. prototype，多例
3. request，请求，web
4. session，session上下文，web
5. application，应用，web

## 单元测试

```java
public class Simple { }

@Test
public void test4() {
    DefaultListableBeanFactory beanFactory =
            parseXml("classpath:framework/beandefinition/bean-scope.xml");
    String beanName = "user1";
    Assertions.assertEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));
}

@Test
public void test5() {
    DefaultListableBeanFactory beanFactory =
            parseXml("classpath:framework/beandefinition/bean-scope.xml");
    String beanName = "user2";
    Assertions.assertNotEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));
}
```

bean-scope.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

	<bean id="user1" class="com.wrp.spring.framework.beandefinition.Simple" scope="prototype"/>
	<bean id="user2" class="com.wrp.spring.framework.beandefinition.Simple" scope="singleton"/>
</beans>
```

## 原理

> 单例bean，有一个缓存，创建后将Bean放入缓存中；多例Bean没有缓存。

```java
protected <T> T doGetBean(
        String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
        throws BeansException {

    String beanName = transformedBeanName(name);
    Object beanInstance;

    //1.查看缓存中是否已经有这个bean了
    Object sharedInstance = getSingleton(beanName);
    if (sharedInstance != null && args == null) {
        // 缓存中存在，并且不是FactoryBean这样的特殊Bean，就直接返回
        beanInstance = getObjectForBeanInstance(sharedInstance, name, beanName, null);
    }

    else {
        // 多例bean检查循环依赖
        if (isPrototypeCurrentlyInCreation(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
        try {
            RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
            if (mbd.isSingleton()) {
                sharedInstance = getSingleton(beanName, () -> {
                    try {
                        return createBean(beanName, mbd, args);
                    }
                    catch (BeansException ex) {
                        destroySingleton(beanName);
                        throw ex;
                    }
                });
                // 通过FactoryBean获取Bean
                beanInstance = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
            }

            else if (mbd.isPrototype()) {
                // It's a prototype -> create a new instance.
                Object prototypeInstance = null;
                try {
                    //将beanName放入正在创建的列表中
                    beforePrototypeCreation(beanName);
                    prototypeInstance = createBean(beanName, mbd, args);
                }
                finally {
                    //将beanName从正在创建的列表中移除
                    afterPrototypeCreation(beanName);
                }
                beanInstance = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
            }
            else {
                // 其他scope
                String scopeName = mbd.getScope();
                Scope scope = this.scopes.get(scopeName);
                try {
                    // 通过scope接口获取bean
                    Object scopedInstance = scope.get(beanName, () -> {
                        beforePrototypeCreation(beanName);
                        try {
                            return createBean(beanName, mbd, args);
                        }
                        finally {
                            afterPrototypeCreation(beanName);
                        }
                    });
                    beanInstance = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
                }
                catch (IllegalStateException ex) {
                    throw new ScopeNotActiveException(beanName, scopeName, ex);
                }
            }
        }
    }

    return adaptBeanInstance(name, beanInstance, requiredType);
}

@Override
protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
        throws BeanCreationException {

    RootBeanDefinition mbdToUse = mbd;

    // 加载Class阶段
    Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
    if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
        mbdToUse = new RootBeanDefinition(mbd);
        mbdToUse.setBeanClass(resolvedClass);
        try {
            mbdToUse.prepareMethodOverrides();
        }
    }

    try {
        // 实例化前操作，如果返回bean，则可以跳过Spring的实例化操作。
        Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
        if (bean != null) {
            return bean;
        }
    }

    try {
        // 创建Bean
        Object beanInstance = doCreateBean(beanName, mbdToUse, args);
        return beanInstance;
    }
}

protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
        throws BeanCreationException {

    BeanWrapper instanceWrapper = null;
    if (mbd.isSingleton()) {
        instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
    }
    if (instanceWrapper == null) {
        // //通过反射调用构造器实例化Bean
        instanceWrapper = createBeanInstance(beanName, mbd, args);
    }
    //变量bean：表示刚刚同构造器创建好的bean示例
    Object bean = instanceWrapper.getWrappedInstance();
    Class<?> beanType = instanceWrapper.getWrappedClass();
    if (beanType != NullBean.class) {
        mbd.resolvedTargetType = beanType;
    }

    //判断是否需要暴露早期的bean
    boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
            isSingletonCurrentlyInCreation(beanName));
    if (earlySingletonExposure) {
        // 暴露早期的Bean到三级缓存中
        addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
    }

    // 初始化
    Object exposedObject = bean;
    try {
        populateBean(beanName, mbd, instanceWrapper);
        exposedObject = initializeBean(beanName, exposedObject, mbd);
    }

    return exposedObject;
}
```

## 扩展-自定义Scope

> 需要自行实现`org.springframework.beans.factory.config.Scope`接口

```java
public interface Scope {

    // 获取bean
	Object get(String name, ObjectFactory<?> objectFactory);

    // 移除bean
	Object remove(String name);

	// 注册销毁时的回调
	void registerDestructionCallback(String name, Runnable callback);

	// 根据key获取上下文中的值,如request中，获取指定属性
	Object resolveContextualObject(String key);

	// 作用域ID
	String getConversationId();
}
```

自定义ThreadScope，（Spring也提供了线程级别的Scope，`org.springframework.context.support.SimpleThreadScope`）

```java
public class MyThreadScope implements Scope {

	public static final String THREAD_SCOPE = "thread";

	ThreadLocal<Map<String, Object>> threadLocal =
			ThreadLocal.withInitial(ConcurrentHashMap::new);

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> map = threadLocal.get();
		Object bean = map.get(name);
		if(bean == null) {
			bean = objectFactory.getObject();
			map.putIfAbsent(name, bean);
		}
		return bean;
	}

	@Override
	public Object remove(String name) {
		return threadLocal.get().remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		System.out.println(name + ": 被移除了");
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return Thread.currentThread().getName();
	}
}
```

```java
	@Test
	public void test6() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/bean-scope.xml");
        // 注册自定义scope
		beanFactory.registerScope(MyThreadScope.THREAD_SCOPE, new MyThreadScope());
		String beanName = "user3";
		Assertions.assertEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));

		CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> beanFactory.getBean(beanName));
		CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> beanFactory.getBean(beanName));
		Simple bean1 = (Simple) future1.join();
		Simple bean2 = (Simple) future2.join();
		Assertions.assertNotEquals(bean1, bean2);
	}
```

bean-scope.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

	<bean id="user1" class="com.wrp.spring.framework.beandefinition.Simple" scope="singleton"/>
	<bean id="user2" class="com.wrp.spring.framework.beandefinition.Simple" scope="prototype"/>
	<bean id="user3" class="com.wrp.spring.framework.beandefinition.Simple" scope="thread"/>
</beans>
```

