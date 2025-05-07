## 基础

> @Configuration + @Bean

```java
public class User { }

@Configuration
public class ConfigBean {

	public ConfigBean() {
		System.out.println("ConfigBean constructor!!!");
	}

	//bean名称为方法默认值：user1
	@Bean
	public User user1() {
		return new User();
	}
}

@Test
public void test1() {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(ConfigBean.class);//@1
    for (String beanName : context.getBeanDefinitionNames()) {
        //别名
        String[] aliases = context.getAliases(beanName);
        System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
                                         beanName,
                                         Arrays.asList(aliases),
                                         context.getBean(beanName)));
    }
}
```

```tex
bean名称:configBean,别名:[],bean对象:com.wrp.spring.lesson001.javabean.ConfigBean$$SpringCGLIB$$0@49a26d19
bean名称:user1,别名:[],bean对象:com.wrp.spring.lesson001.javabean.User@730e5763
bean名称:user2Bean,别名:[],bean对象:com.wrp.spring.lesson001.javabean.User@7275c74b
bean名称:user3Bean,别名:[user3BeanAlias2, user3BeanAlias1],bean对象:com.wrp.spring.lesson001.javabean.User@19058533
```

