## 1. @Configuration + @Bean

```java
@Configuration
public class MainConfig1 {

	@Bean
	public Service1 service1() {
		return new Service1();
	}

	@Bean
	public Service2 service2() {
		return new Service2();
	}

	@Bean
	public Service3 service3() {
		return new Service3();
	}

}

@Test
public void test1() {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(MainConfig1.class);
    context.close();
}
```

结果：创建顺序和声明顺序相同，销毁顺序与创建顺序相反

```tex
create Service1
create Service2
create Service3
destroy Service3
destroy Service2
destroy Service1
```

## 2. @ComponentScan + @Component

```java
@ComponentScan
@Configuration
public class MainConfig2 {
}

@Test
public void test2() {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(MainConfig2.class);
    context.close();
}
```

