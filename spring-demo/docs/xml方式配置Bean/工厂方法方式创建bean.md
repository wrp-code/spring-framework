## 工厂方法方式创建Bean

1. 静态工厂方法创建Bean
2. 实例工厂方法创建Bean

## 静态工厂单元测试

```java
public class StaticFactory {

	public static User createUser() {
		System.out.println("静态工厂方法创建User");
		return new User();
	}
}

@Test
public void test2() {
    String xml = "classpath:framework/beandefinition/staticfactory-createbean.xml";
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    int beanCount = reader.loadBeanDefinitions(xml);
    Assertions.assertEquals(1, beanCount);
    Assertions.assertTrue(beanFactory.containsBeanDefinition("staticUserFactory"));
    Assertions.assertEquals(beanFactory.getBean("staticUserFactory", User.class),
                            beanFactory.getBean("staticUserFactory", User.class));
}
```

## 实例工厂单元测试

```java
public class UserFactory {

	public User createUser() {
		System.out.println("实例工厂方法模式创建User");
		return new User();
	}
}

@Test
public void test3() {
    String xml = "classpath:framework/beandefinition/instancefactory-createbean.xml";
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    int beanCount = reader.loadBeanDefinitions(xml);
    Assertions.assertEquals(2, beanCount);
    Assertions.assertTrue(beanFactory.containsBeanDefinition("userFactory"));
    Assertions.assertTrue(beanFactory.containsBeanDefinition("user3"));
    Assertions.assertEquals(beanFactory.getBean("user3", User.class),
          beanFactory.getBean("user3", User.class));
}
```

## 源码分析

将断点打在静态方法内部。

```java
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
    

    // 工厂方法不为空，静态工厂方法或者实例工厂方法创建bean
    if (mbd.getFactoryMethodName() != null) {
       return instantiateUsingFactoryMethod(beanName, mbd, args);
    }

    // 探测构造器钩子
    Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
    if (ctors != null || mbd.getResolvedAutowireMode() == AUTOWIRE_CONSTRUCTOR ||
          mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
       // 有候选构造器 || 构造器自动注入模式 || 有构造器参数 || args不为空
       return autowireConstructor(beanName, mbd, ctors, args);
    }

    // 使用无参构造
    return instantiateBean(beanName, mbd);
}

protected BeanWrapper instantiateUsingFactoryMethod(
	String beanName, RootBeanDefinition mbd, @Nullable Object[] explicitArgs) {

	return new ConstructorResolver(this).instantiateUsingFactoryMethod(beanName, mbd, explicitArgs);
}
```

最终调用`org.springframework.beans.factory.support.ConstructorResolver#instantiateUsingFactoryMethod`

```java
public BeanWrapper instantiateUsingFactoryMethod(
       String beanName, RootBeanDefinition mbd, @Nullable Object[] explicitArgs) {

    

    Object factoryBean;
    Class<?> factoryClass;
    boolean isStatic;

    String factoryBeanName = mbd.getFactoryBeanName();
    if (factoryBeanName != null) {
       //不为空，说明是实例工厂方法
        factoryBean = this.beanFactory.getBean(factoryBeanName);
        factoryClass = factoryBean.getClass();
        isStatic = false;
    }
    else {
       // 静态工厂方法
       factoryBean = null;
       factoryClass = mbd.getBeanClass();
       isStatic = true;
    }

    Method factoryMethodToUse = null;
    ArgumentsHolder argsHolderToUse = null;
    Object[] argsToUse = null;

    if (explicitArgs != null) {
       argsToUse = explicitArgs;
    }
    else {
       Object[] argsToResolve = null;
       synchronized (mbd.constructorArgumentLock) {
          factoryMethodToUse = (Method) mbd.resolvedConstructorOrFactoryMethod;
          if (factoryMethodToUse != null && mbd.constructorArgumentsResolved) {
             // Found a cached factory method...
             argsToUse = mbd.resolvedConstructorArguments;
             if (argsToUse == null) {
                argsToResolve = mbd.preparedConstructorArguments;
             }
          }
       }
       if (argsToResolve != null) {
          argsToUse = resolvePreparedArguments(beanName, mbd, bw, factoryMethodToUse, argsToResolve);
       }
    }

    if (factoryMethodToUse == null || argsToUse == null) {
       // Need to determine the factory method...
       // Try all methods with this name to see if they match the given arguments.
       factoryClass = ClassUtils.getUserClass(factoryClass);

       List<Method> candidates = null;
       if (mbd.isFactoryMethodUnique) {
          if (factoryMethodToUse == null) {
             factoryMethodToUse = mbd.getResolvedFactoryMethod();
          }
          if (factoryMethodToUse != null) {
             candidates = Collections.singletonList(factoryMethodToUse);
          }
       }
       if (candidates == null) {
          candidates = new ArrayList<>();
          Method[] rawCandidates = getCandidateMethods(factoryClass, mbd);
          for (Method candidate : rawCandidates) {
             if ((!isStatic || isStaticCandidate(candidate, factoryClass)) && mbd.isFactoryMethod(candidate)) {
                 // (不是静态方法 || 是工厂类的静态方法) && 是工厂方法
                candidates.add(candidate);
             }
          }
       }
    }
    // 调用InstantiationStrategy
    bw.setBeanInstance(instantiate(beanName, mbd, factoryBean, factoryMethodToUse, argsToUse));
    return bw;
}
```