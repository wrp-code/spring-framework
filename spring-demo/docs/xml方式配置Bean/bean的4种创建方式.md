## bean的4种创建方式

1. 构造器
2. 静态工厂方法
3. 实例工厂方法
4. FactoryBean

## 1. 单元测试

```java
@Data
public class User {
	String name;
	User innerUser;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}
}

public class StaticFactory {

	public static User createUser() {
		System.out.println("静态工厂方法创建User");
		return new User();
	}
}

public class UserFactory {

	public User createUser() {
		System.out.println("实例工厂方法模式创建User");
		return new User();
	}
}


public class UserFactoryBean implements FactoryBean<User> {
	@Override
	public User getObject() throws Exception {
		System.out.println("FactoryBean方式创建User");
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
```

```java
@Test
public void test3() {
    DefaultListableBeanFactory beanFactory =
            parseXml("classpath:framework/beandefinition/createbean.xml");
    for (String beanName : beanFactory.getBeanDefinitionNames()) {
        System.out.printf("beanName: %s，bean: %s\n", beanName, beanFactory.getBean(beanName));
    }
}
```

createbean.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

<!--	方式1：构造器-->
	<bean id="user1" class="com.wrp.spring.framework.beandefinition.User"/>
	<bean id="user2" class="com.wrp.spring.framework.beandefinition.User">
		<constructor-arg name="name" value="wrp" />
	</bean>

<!--	方式2：静态工厂方法-->
	<bean id="staticUserFactory" class="com.wrp.spring.framework.beandefinition.StaticFactory"
		  factory-method="createUser"/>

<!--	方式3：实例工厂方法-->
	<bean id="userFactory" class="com.wrp.spring.framework.beandefinition.UserFactory"/>
	<bean id="user3" factory-bean="userFactory" factory-method="createUser"/>

<!--	方法4：FactoryBean方法-->
	<bean id="userFactoryBean" class="com.wrp.spring.framework.beandefinition.UserFactoryBean"/>

</beans>
```

结果

```tex
beanName: user1，bean: User(name=null, innerUser=null)
beanName: user2，bean: User(name=wrp, innerUser=null)
静态工厂方法创建User
beanName: staticUserFactory，bean: User(name=null, innerUser=null)
beanName: userFactory，bean: com.wrp.spring.framework.beandefinition.UserFactory@2b8bd14b
实例工厂方法模式创建User
beanName: user3，bean: User(name=null, innerUser=null)
FactoryBean方式创建User
beanName: userFactoryBean，bean: User(name=null, innerUser=null)
```

结论

1. 静态工厂不会被注册成Bean
2. 实例工厂会被注册成Bean
3. `FactoryBean`被注册成Bean，但是getBean时，返回的是getObject方法的结果

## 2. 构造器方式创建Bean的原理

> 底层依赖`org.springframework.beans.factory.support.ConstructorResolver`类和`org.springframework.beans.factory.support.InstantiationStrategy`接口
>
> 在`AbstractAutowireCapableBeanFactory`中默认创建`CglibSubclassingInstantiationStrategy`实例

```java
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
    Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
    if (ctors != null || mbd.getResolvedAutowireMode() == AUTOWIRE_CONSTRUCTOR ||
            mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
        // 有候选构造器 || 构造器自动注入模式 || 有构造器参数 || args不为空
        return autowireConstructor(beanName, mbd, ctors, args);
    }

    // Preferred constructors for default construction?
    // 如果设置了有限的构造器，则使用
    ctors = mbd.getPreferredConstructors();
    if (ctors != null) {
        return autowireConstructor(beanName, mbd, ctors, null);
    }

    // 使用无参构造
    return instantiateBean(beanName, mbd);
}
    protected BeanWrapper autowireConstructor(
        String beanName, RootBeanDefinition mbd, @Nullable Constructor<?>[] ctors, @Nullable Object[] explicitArgs) {
		// 探测出合适的构造器，底层也是使用的InstantiationStrategy进行实例化
		return new ConstructorResolver(this).autowireConstructor(beanName, mbd, ctors, explicitArgs);
	}
    
    protected BeanWrapper instantiateBean(String beanName, RootBeanDefinition mbd) {
        try {
            // getInstantiationStrategy默认是 CglibSubclassingInstantiationStrategy
            Object beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, this);
            BeanWrapper bw = new BeanWrapperImpl(beanInstance);
            initBeanWrapper(bw);
            return bw;
        }
        catch (Throwable ex) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, ex.getMessage(), ex);
        }
	}
}

// 简单的实例化策略
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
	public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
		if (!bd.hasMethodOverrides()) {
			Constructor<?> constructorToUse;
			synchronized (bd.constructorArgumentLock) {
				constructorToUse = (Constructor<?>) bd.resolvedConstructorOrFactoryMethod;
				if (constructorToUse == null) {
					Class<?> clazz = bd.getBeanClass();
					if (clazz.isInterface()) {
						throw new BeanInstantiationException(clazz, "Specified class is an interface");
					}
					try {
                        // 返回无参构造器
						constructorToUse = clazz.getDeclaredConstructor();
						bd.resolvedConstructorOrFactoryMethod = constructorToUse;
					}
					catch (Throwable ex) {
						throw new BeanInstantiationException(clazz, "No default constructor found", ex);
					}
				}
			}
            // 底层调用反射
			return BeanUtils.instantiateClass(constructorToUse);
		}
		else {
			// Must generate CGLIB subclass.
			return instantiateWithMethodInjection(bd, beanName, owner);
		}
	}
}

public abstract class BeanUtils {
 	public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
		try {
			ReflectionUtils.makeAccessible(ctor);
			if (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(ctor.getDeclaringClass())) {
				return KotlinDelegate.instantiateClass(ctor, args);
			}
			else {
				int parameterCount = ctor.getParameterCount();
                // 无参构造
				if (parameterCount == 0) {
					return ctor.newInstance();
				}
				Class<?>[] parameterTypes = ctor.getParameterTypes();
				Object[] argsWithDefaultValues = new Object[args.length];
				for (int i = 0 ; i < args.length; i++) {
					if (args[i] == null) {
						Class<?> parameterType = parameterTypes[i];
						argsWithDefaultValues[i] = (parameterType.isPrimitive() ? DEFAULT_TYPE_VALUES.get(parameterType) : null);
					}
					else {
						argsWithDefaultValues[i] = args[i];
					}
				}
                // 有参构造
				return ctor.newInstance(argsWithDefaultValues);
			}
		}
	}   
}
```

## 3. 静态工厂方法创建Bean的原理

> 底层也是依赖`org.springframework.beans.factory.support.ConstructorResolver`类和`org.springframework.beans.factory.support.InstantiationStrategy`接口，前者查询工厂方法，后者进行实例化

```java
// org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBeanInstance
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
    // 工厂方法不为空，静态工厂方法或者实例工厂方法创建bean
    if (mbd.getFactoryMethodName() != null) {
        return instantiateUsingFactoryMethod(beanName, mbd, args);
    }

    protected BeanWrapper instantiateUsingFactoryMethod(
        String beanName, RootBeanDefinition mbd, @Nullable Object[] explicitArgs) {
		return new ConstructorResolver(this).instantiateUsingFactoryMethod(beanName, mbd, explicitArgs);
	}
}

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
	public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner,
			@Nullable Object factoryBean, Method factoryMethod, Object... args) {

		return instantiateWithFactoryMethod(factoryMethod, () -> {
			try {
				ReflectionUtils.makeAccessible(factoryMethod);
                // 反射调用方法
				Object result = factoryMethod.invoke(factoryBean, args);
				if (result == null) {
					result = new NullBean();
				}
				return result;
			}
		});
	}
    
    public static <T> T instantiateWithFactoryMethod(Method method, Supplier<T> instanceSupplier) {
        // 之前调用的工厂方法
		Method priorInvokedFactoryMethod = currentlyInvokedFactoryMethod.get();
		try {
            //
			currentlyInvokedFactoryMethod.set(method);
			return instanceSupplier.get();
		}
		finally {
			if (priorInvokedFactoryMethod != null) {
				currentlyInvokedFactoryMethod.set(priorInvokedFactoryMethod);
			}
			else {
				currentlyInvokedFactoryMethod.remove();
			}
		}
	}
}

```

## 4. 实例工厂方法创建Bean的原理

> 和静态工厂方法相同，最终会在`ConstructorResolver`中判断调用的方法，通过`InstantiationStrategy`进行实例化

## 5. FactoryBean方法创建Bean的原理

> 

```java
protected <T> T doGetBean(
			String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
			throws BeansException {
    if (mbd.isSingleton()) {
        // 通过无参构造获取到FactoryBean
        sharedInstance = getSingleton(beanName, () -> {
            try {
                return createBean(beanName, mbd, args);
            }
            catch (BeansException ex) {
                // Explicitly remove instance from singleton cache: It might have been put there
                // eagerly by the creation process, to allow for circular reference resolution.
                // Also remove any beans that received a temporary reference to the bean.
                destroySingleton(beanName);
                throw ex;
            }
        });
        // 通过FactoryBean获取Bean
        beanInstance = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
    }
}

@Override
protected Object getObjectForBeanInstance(
        Object beanInstance, String name, String beanName, @Nullable RootBeanDefinition mbd) {

    String currentlyCreatedBean = this.currentlyCreatedBean.get();
    if (currentlyCreatedBean != null) {
        registerDependentBean(beanName, currentlyCreatedBean);
    }

    return super.getObjectForBeanInstance(beanInstance, name, beanName, mbd);
}

protected Object getObjectForBeanInstance(
        Object beanInstance, String name, String beanName, @Nullable RootBeanDefinition mbd) {

    // Don't let calling code try to dereference the factory if the bean isn't a factory.
    if (BeanFactoryUtils.isFactoryDereference(name)) {
        if (beanInstance instanceof NullBean) {
            return beanInstance;
        }
        if (!(beanInstance instanceof FactoryBean)) {
            throw new BeanIsNotAFactoryException(beanName, beanInstance.getClass());
        }
        if (mbd != null) {
            mbd.isFactoryBean = true;
        }
        return beanInstance;
    }

    // 如果不是FactoryBean直接返回
    if (!(beanInstance instanceof FactoryBean<?> factoryBean)) {
        return beanInstance;
    }

    Object object = null;
    if (mbd != null) {
        mbd.isFactoryBean = true;
    }
    else {
        object = getCachedObjectForFactoryBean(beanName);
    }
    if (object == null) {
        // Return bean instance from factory.
        // Caches object obtained from FactoryBean if it is a singleton.
        if (mbd == null && containsBeanDefinition(beanName)) {
            mbd = getMergedLocalBeanDefinition(beanName);
        }
        boolean synthetic = (mbd != null && mbd.isSynthetic());
        // 获取Bean
        object = getObjectFromFactoryBean(factoryBean, beanName, !synthetic);
    }
    return object;
}

protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName, boolean shouldPostProcess) {
    // 单例
    if (factory.isSingleton() && containsSingleton(beanName)) {
        this.singletonLock.lock();
        try {
            // 从缓存中获取
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
                if (alreadyThere != null) {
                    object = alreadyThere;
                }
                else {
                    if (shouldPostProcess) {
                        if (isSingletonCurrentlyInCreation(beanName)) {
                            // Temporarily return non-post-processed object, not storing it yet
                            return object;
                        }
                        beforeSingletonCreation(beanName);
                        try {
                            object = postProcessObjectFromFactoryBean(object, beanName);
                        }
                        catch (Throwable ex) {
                            throw new BeanCreationException(beanName,
                                    "Post-processing of FactoryBean's singleton object failed", ex);
                        }
                        finally {
                            afterSingletonCreation(beanName);
                        }
                    }
                    if (containsSingleton(beanName)) {
                        // 保存到缓存
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                }
            }
            return object;
        }
        finally {
            this.singletonLock.unlock();
        }
    }
    else {
        // 多例，直接创建
        Object object = doGetObjectFromFactoryBean(factory, beanName);
        if (shouldPostProcess) {
            try {
                object = postProcessObjectFromFactoryBean(object, beanName);
            }
        }
        return object;
    }
}

private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) throws BeanCreationException {
    Object object;
    try {
        // 调用FactoryBean的getObject()方法
        object = factory.getObject();
    }

    return object;
}
```

