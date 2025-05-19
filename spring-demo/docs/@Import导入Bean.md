## 1. 使用方式
@Import中如果value有多个，默认会顺序执行
### 1.1 导入普通类
> 普通类： 没有任何注解或者有@Component、@Service等注解的类
> 
```java
public class Service { }

@Component
public class Service2 { }

@Import({Service.class, Service2.class})
public class DemoConfig1 { }

@Test
public void test1() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig1.class);
    for (String beanDefinitionName : context.getBeanDefinitionNames()) {
        System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
    }
}
```

结果Service和Service2均注册到容器中了

### 1.2 导入配置类

> 配置类： 有@Configuration注解的类

```java
@Configuration
public class MyConfig {

	@Bean
	public User user(String name) {
		return new User(name);
	}

	@Bean
	public String address() {
		return "chengdu";
	}
}

public class MyConfig2 {

	
    @Bean
	public String name() {
		return "wrp";
	}
}

@Import({MyConfig.class, MyConfig2.class})
public class DemoConfig2 {}

@Test
public void test2() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig2.class);
    for (String beanDefinitionName : context.getBeanDefinitionNames()) {
        System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
    }
}
```

结果表明

1. 被@Import导入的类，不管是否添加了@Configuration，均会处理标注@Bean的方法

```tex
beanName: demoConfig2, com.wrp.spring.framework.importclass.demo2.DemoConfig2@dab48d3
beanName: com.wrp.spring.framework.importclass.demo2.MyConfig, com.wrp.spring.framework.importclass.demo2.MyConfig$$SpringCGLIB$$0@58a2b4c
beanName: user, User(name=wrp, innerUser=null)
beanName: address, chengdu
beanName: com.wrp.spring.framework.importclass.demo2.MyConfig2, com.wrp.spring.framework.importclass.demo2.MyConfig2@7159a5cd
beanName: name, wrp
```

### 1.3 导入扫描类
> 扫描类： 有@ComponentScan注解的类

类结构如下：

module1

​	Service1

​	Service2

​	ModuleConfig1

module2

​	Service3

​	Service4

​	ModuleConfig2

DemoConfig3

```java
@Import({ModuleConfig1.class, ModuleConfig2.class})
public class DemoConfig3 {}
```

如果需要导入不同的模块，只需要调整@Import的属性即可。

### 1.4 导入ImportBeanDefinitionRegistrar接口

> 通过接口的方式注册BeanDefinition
> 

```java
public interface ImportBeanDefinitionRegistrar {

    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry,
          BeanNameGenerator importBeanNameGenerator) {
       //importingClassMetadata 获取被@Import注解标注的类所有注解的信息
       registerBeanDefinitions(importingClassMetadata, registry);
    }

    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    }

}
```

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//定义一个bean：Service1
		BeanDefinition service1BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service1.class).getBeanDefinition();
		//注册bean
		registry.registerBeanDefinition("service1", service1BeanDinition);

		//定义一个bean：Service2，通过addPropertyReference注入service1
		BeanDefinition service2BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service2.class).
				addPropertyReference("service1", "service1").
				getBeanDefinition();
		//注册bean
		registry.registerBeanDefinition("service2", service2BeanDinition);
	}
}

@Import({MyImportBeanDefinitionRegistrar.class})
public class MainConfig4 {}
```

### 1.5 导入ImportSelector接口

> 通过接口的方式返回需要导入的全限定类名数组
> 

```java
public interface ImportSelector {

    /**
     * 返回需要导入的全限定类名的数组，可以是任何普通类，配置类（@Configuration、@Bean、@ComponentScan等标注的类）
     * importingClassMetadata：用来获取被@Import标注的类上面所有的注解信息
     */
    String[] selectImports(AnnotationMetadata importingClassMetadata);

    /**
		排除导入的类过滤器
     */
    @Nullable
    default Predicate<String> getExclusionFilter() {
       return null;
    }

}
```

### 1.6 导入DeferredImportSelector接口

> DeferredImportSelector接口是ImportSelector的子接口
> 
> 与ImportSelector的区别：
> 1. 延迟导入，优先级低于其他导入类，会最后处理DeferredImportSelector（结合@Conditional进行条件注入）
> 2. 指定导入的类的处理顺序（DeferredImportSelector会被排序）
> 

测试1

```java
// 导入的Bean如果名称相同，后面的会覆盖前面的
@Import({
       MyDeferredImportSelector.class,
       Config1.class,
       MyImportSelector.class
})
public class DemoConfig7 {
}
```

- `MyDeferredImportSelector`放在Import数组的第一个，但是依然会被最后处理
- Import导入的Bean如果名称相同，后面的会覆盖前面的
- Import数组顺序执行导入

测试2

```java
@Import({
       MyDeferredImportSelector.class,
       MyDeferredImportSelector2.class
})
public class DemoConfig8 {
}
```

- MyDeferredImportSelector和MyDeferredImportSelector2均实现了Ordered接口，前者优先级更低，所有即使在Import数组前面，也会在后者后面执行。

## 2. 核心原理

> `ConfigurationClassPostProcessor`处理@Import注解，ConfigurationClassPostProcessor实现了BeanDefinitionRegistryPostProcessor，参与到BeanFactory的生命周期中。内部委托`ConfigurationClassParser`，在解析过程中处理@Import

```java
protected final SourceClass doProcessConfigurationClass(
       ConfigurationClass configClass, SourceClass sourceClass, Predicate<String> filter)
       throws IOException {


    // Process any @Import annotations
    processImports(configClass, sourceClass, getImports(sourceClass), filter, true);



    // Process superclass, if any
    if (sourceClass.getMetadata().hasSuperClass()) {
       String superclass = sourceClass.getMetadata().getSuperClassName();
       if (superclass != null && !superclass.startsWith("java")) {
          boolean superclassKnown = this.knownSuperclasses.containsKey(superclass);
          this.knownSuperclasses.add(superclass, configClass);
          if (!superclassKnown) {
             // Superclass found, return its annotation metadata and recurse
             // 递归解析父类
             return sourceClass.getSuperClass();
          }
       }
    }

    // No superclass -> processing is complete
    return null;
}
```



## 3. 源码分析




## 4. 总结

