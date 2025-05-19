## 1. @Conditional

> spring4.0开始， @Conditionnal可以增加条件判断，当所有条件都满足时，Bean才会被Spring容器处理

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

    Class<? extends Condition>[] value();

}
```

value 是一个Condition类型的类数组，当所有Condition都成立的时候，@Conditional的结果才成立

```java
@FunctionalInterface
public interface Condition {

	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);

}
```

- context 条件上下文
- metadata 获取被@Conditional标注的对象上的所有注解信息

```java
public interface ConditionContext {

	BeanDefinitionRegistry getRegistry();

	@Nullable
	ConfigurableListableBeanFactory getBeanFactory();

	Environment getEnvironment();

	ResourceLoader getResourceLoader();

	@Nullable
	ClassLoader getClassLoader();

}
```

### 1.1 配置类

> `org.springframework.context.annotation.ConfigurationClassUtils#checkConfigurationClassCandidate`判断类是否为配置类

1. 类上有@Compontent注解
2. 类上有@Configuration注解
3. 类上有@CompontentScan注解
4. 类上有@Import注解
5. 类上有@ImportResource注解
6. 类中有@Bean标注的方法

### 1.2 两阶段处理

> Spring在`org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions`中处理

阶段一：配置解析阶段`org.springframework.context.annotation.ConfigurationClassParser#parse(java.util.Set<org.springframework.beans.factory.config.BeanDefinitionHolder>)`

阶段二：注册BeanDefinition阶段`org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitions`

**`@Conditional`条件注入可以控制这两个阶段，控制是否解析配置类、控制是否注册BeanDefinition**

### 1.3 Condition和ConfigurationCondition区别

- Condition接口对两个阶段都有效
- ConfigurationCondition 是Condition的子接口，可以分阶段进行控制，更灵活

```java
public interface ConfigurationCondition extends Condition {

	ConfigurationPhase getConfigurationPhase();


	/**
	 *  表示阶段的枚举：2个值
	 */
	enum ConfigurationPhase {

		/**
		 * 配置类解析阶段，如果条件为false，配置类将不会被解析
		 */
		PARSE_CONFIGURATION,

		/**
		 * bean注册阶段，如果为false，bean将不会被注册
		 */
		REGISTER_BEAN
	}

}
```

## 2. 使用方法

1. 自定义一个类，实现Condition或ConfigurationCondition接口
2. 标注@Conditional注解，指定条件类

### 2.1 自定义Condition 

> 实现`Condition`接口

```java
public class MyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
       return false;
    }
}
```

测试发现只有`address2`注册成功。

- @Conditional放在类上时，在解析阶段生效
- @Conditional放在方法上时，在Bean注册阶段生效

```java
@Conditional(MyCondition.class)
@Configuration
public class DemoConfig1 {

    @Bean
    public String name1() {
       return "wrp1";
    }

    @Bean
    public String address1() {
       return "成都1";
    }
}

@Configuration
public class DemoConfig2 {

	@Conditional(MyCondition.class)
	@Bean
	public String name2() {
		return "wrp2";
	}

	@Bean
	public String address2() {
		return "成都2";
	}
}

@Import({DemoConfig1.class, DemoConfig2.class})
public class ImportConfig {}
```

### 2.2 自定义注解 + 自定义Condition

> 在自定义注解上间接标注了`@Conditional`注解，使用自定义条件类`OnBeanCondition`

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnBeanCondition.class)
public @interface ConditionalOnMissingBean {
    Class<?>[] value() default {};

}
```

自定义条件类，当不存在任何value指定的类型Bean时返回true，条件成立；

```java
public class OnBeanCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Class[] o = (Class[]) metadata.getAnnotationAttributes(ConditionalOnMissingBean.class.getName()).get("value");
		for (Class c : o) {
			if(!context.getBeanFactory().getBeansOfType(c).isEmpty()) {
				return false;
			}
		}

		return true;
	}
}
```

测试，结果只有service1被注入

```java
public class Config {

    @ConditionalOnMissingBean(IService.class)
    @Bean
    public IService service1() {
       return new Service1();
    }

    @ConditionalOnMissingBean(IService.class)
    @Bean
    public IService service2() {
       return new Service2();
    }
}
```

### 2.3 多个Condition

> 默认按照@Conditional的value来顺序执行。
>
> 可以指定顺序**PriorityOrdered asc,order值 asc**

```java
@Order(1)
class Condition1 implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}
}

class Condition2 implements Condition, Ordered {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}

class Condition3 implements Condition, PriorityOrdered {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}

	@Override
	public int getOrder() {
		return 10;
	}
}

@Configuration
@Conditional({Condition1.class, Condition2.class, Condition3.class})
public class DemoConfig3 {
}
```

- 不设置order时，Condtion1->Condtion2->Condtion3
- 设置order时，Condtion3->Condtion2->Condtion1

### 2.4 使用ConfigurationCondition精确控制

> 判断bean存不存在的问题，通常会使用ConfigurationCondition这个接口。
>
> Springboot中，对@Conditionxxx这样的注解，可以去看一下这些注解，很多都实现了ConfigurationCondition接口

```java
public class MyConfigurationCondition1 implements ConfigurationCondition {
	@Override
	public ConfigurationPhase getConfigurationPhase() {
        // 精确控制条件是在解析配置阶段、注册Bean的哪个阶段生效
		return ConfigurationPhase.REGISTER_BEAN;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return !context.getBeanFactory()
				.getBeansOfType(IService.class)
				.isEmpty();
	}
}
```

## 3. 核心原理



## 4. 源码分析

