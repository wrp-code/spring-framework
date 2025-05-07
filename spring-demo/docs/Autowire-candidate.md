## autowire-candidate
> BeanDefinition的`autowireCandidate`属性，是否是自动注入的候选Bean
> 
## 测试

类B中自动注入类A

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

a1设置autowireCandidate = false，不作为候选的Bean被注入

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

当类A有多个，且仅有一个Bean的autowireCandidate为true时，getBean(Class)可以获取到autowireCandidate=true的Bean。

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

## 结论

1. autowire-candidate=false的Bean也会被注入到Spring容器中
1. autowire-candidate=false不会作为候选Bean被注入
1. 仅有一个Bean，getBean一定能获取到
1. 多个Bean，且仅有一个Bean的autowire-candidate=true时，getBean(Class)才能获取到

## 原理

