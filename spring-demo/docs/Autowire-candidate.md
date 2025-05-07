## autowire-candidate
> BeanDefinition的`autowireCandidate`属性，是否是自动注入的候选Bean
> 
```java
public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor
		implements BeanDefinition, Cloneable {
    
	private boolean autowireCandidate = true;
    
    @Override
	public void setAutowireCandidate(boolean autowireCandidate) {
		this.autowireCandidate = autowireCandidate;
	}

	@Override
	public boolean isAutowireCandidate() {
		return this.autowireCandidate;
	}
}
```

## 测试

### 准备类

```java
@ToString
public class A implements BeanNameAware {

	String name;

	@Override
	public void setBeanName(String name) {
		this.name = name;
	}
}

public class B {
    @Autowired
    A a;
}

public class C {

	@Autowired
	List<A> a;
}
```
### 测试1

系统中存在两个A，B在创建过程中需要注入A，发现两个Bean，报错`UnsatisfiedDependencyException`。

```java
@Configuration
public class Config {

    @Bean
    public A a1() {
        return new A();
    }

    @Bean
    public A a2() {
        return new A();
    }

    @Bean
    public B b() {
        return new B();
    }
}

@Test
public void test() {
    Assertions.assertThrows(UnsatisfiedDependencyException.class,
            ()-> new AnnotationConfigApplicationContext(Config.class));
}
```

### 测试2

- B中注入的A是a2

- getBean(Class)是a2
- getBean可以获取a1

```java
@Configuration
public class Config1 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean
	public A a2() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}
}

@Test
public void test1() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config1.class);
    Assertions.assertEquals(context.getBean(B.class).a, context.getBean("a2", A.class));
    Assertions.assertEquals(context.getBean(A.class), context.getBean("a2", A.class));
    Assertions.assertNotNull(context.getBean("a1", A.class));
}
```

### 测试3

类A仅一个，且autowireCandidate = false时，B无法创建，报错`UnsatisfiedDependencyException`。

- a无法自动注入

```java
@Configuration
public class Config5 {

	@Bean(autowireCandidate = false)
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}
}

@Test
public void test5() {
    Assertions.assertThrows(UnsatisfiedDependencyException.class,
          () -> new AnnotationConfigApplicationContext(Config5.class));
}
```

### 测试4

- getBean(Class)是a3

```java
@Configuration
public class Config3 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean(autowireCandidate = false)
	public A a2() {
		return new A();
	}

	@Bean
	public A a3() {
		return new A();
	}
}

@Test
public void test3() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config3.class);
    Assertions.assertEquals(context.getBean(A.class).name, "a3");
}
```

### 测试5

getBean(Class)，且容器中存在多个autowire-candidate=true时，报错`NoUniqueBeanDefinitionException`。

```java
@Configuration
public class Config4 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean
	public A a2() {
		return new A();
	}

	@Bean
	public A a3() {
		return new A();
	}
}

@Test
public void test4() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config4.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
            () -> context.getBean(A.class));
}
```

### 测试6

一个autowireCandidate = false，能够获取到

```java
@Configuration
public class Config2 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}
}

@Test
public void test2() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config2.class);
    Assertions.assertNotNull(context.getBean(A.class));
    Assertions.assertEquals(context.getBean(A.class).name, "a1");
}
```

### 测试7

两个autowireCandidate = false，报错

```java
@Configuration
public class Config6 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean(autowireCandidate = false)
	public A a2() {
		return new A();
	}
}

@Test
public void test6() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config6.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
            () -> context.getBean(A.class));
}
```

### 测试8

注入c中的a只有一个

```java
@Configuration
public class Config7 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean(autowireCandidate = false)
	public A a2() {
		return new A();
	}

	@Bean
	public A a3() {
		return new A();
	}

	@Bean
	public C c() {
		return new C();
	}
}

@Test
public void test7() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config7.class);
    Assertions.assertEquals(1, context.getBean(C.class).a.size());
}
```

## 结论

1. autowire-candidate=false的Bean也会被注册到Spring容器中
1. autowire-candidate=false不会作为候选Bean被自动注入
1. 仅有一个Bean，getBean一定能获取到
1. 多个Bean，且仅有一个Bean的autowire-candidate=true时，getBean(Class)才能获取到

## 原理

### autowire-candidate=false不会作为候选Bean被自动注入

在创建Bean时发现需要注入属性，就会去Spring容器中找符合类型的自动注入候选者Bean，即autowire-candidate=true的Bean。

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
    
    protected Map<String, Object> findAutowireCandidates(
			@Nullable String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {

        // 找到应用中的所有候选者
		String[] candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				this, requiredType, true, descriptor.isEager());
		Map<String, Object> result = CollectionUtils.newLinkedHashMap(candidateNames.length);
		// ignore...
		for (String candidate : candidateNames) {
            // isAutowireCandidate最后会调用BeanDefinition的isAutowireCandidate方法
			if (!isSelfReference(beanName, candidate) && isAutowireCandidate(candidate, descriptor)) {
				addCandidateEntry(result, candidate, descriptor, requiredType);
			}
		}
		// ignore...
		return result;
	}
}
```

### autowire-candidate=false的Bean也会被注册到Spring容器中

autowire-candidate只是BeanDefinition的一个属性，在解析BeanDefinition后均会被注册到Spring容器中。在`org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry`中处理@Configuration+@Bean，最终委托给`ConfigurationClassBeanDefinitionReader`类

```java
//org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod
private void loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod) {
    ConfigurationClassBeanDefinition beanDef = new ConfigurationClassBeanDefinition(configClass, metadata, beanName);
    
    boolean autowireCandidate = bean.getBoolean("autowireCandidate");
    if (!autowireCandidate) {
        beanDef.setAutowireCandidate(false);
    }

    boolean defaultCandidate = bean.getBoolean("defaultCandidate");
    if (!defaultCandidate) {
        beanDef.setDefaultCandidate(false);
    }
    BeanDefinition beanDefToRegister = beanDef;
    this.registry.registerBeanDefinition(beanName, beanDefToRegister);
}
```

