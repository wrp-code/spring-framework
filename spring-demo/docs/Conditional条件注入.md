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

> @Configuration +@Conditional为例。

### 3.1 解析配置类阶段

1. 处理配置类方法中，根据条件先校验是否需要跳过配置类。conditionEvaluator是`ConditionEvaluator`类型

```java
protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) {
    // @Import条件注入
    if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
       return;
    }
}
```

2. `ConditionEvaluator`判断是否应该跳过配置类

```java
public boolean shouldSkip(@Nullable AnnotatedTypeMetadata metadata, @Nullable ConfigurationPhase phase) {
		// 不包含Conditional，直接跳过
		if (metadata == null || !metadata.isAnnotated(Conditional.class.getName())) {
			return false;
		}

		if (phase == null) {
			if (metadata instanceof AnnotationMetadata annotationMetadata &&
					ConfigurationClassUtils.isConfigurationCandidate(annotationMetadata)) {
				return shouldSkip(metadata, ConfigurationPhase.PARSE_CONFIGURATION);
			}
			return shouldSkip(metadata, ConfigurationPhase.REGISTER_BEAN);
		}

		List<Condition> conditions = collectConditions(metadata);
		for (Condition condition : conditions) {
			ConfigurationPhase requiredPhase = null;
			if (condition instanceof ConfigurationCondition configurationCondition) {
				requiredPhase = configurationCondition.getConfigurationPhase();
			}
			// requiredPhase == null，Condition接口
			// requiredPhase == phase，ConfigurationCondition接口必须先匹配阶段
			// matches = false时会返回true，即跳过当前指定阶段
			if ((requiredPhase == null || requiredPhase == phase) && !condition.matches(this.context, metadata)) {
				return true;
			}
		}

		return false;
	}
```

- `collectConditions`方法是收集配置的条件集合，内部通过反射获取条件类，并具有排序功能

```java
List<Condition> collectConditions(@Nullable AnnotatedTypeMetadata metadata) {
    if (metadata == null || !metadata.isAnnotated(Conditional.class.getName())) {
        return Collections.emptyList();
    }

    List<Condition> conditions = new ArrayList<>();
    // 通过注解辕信息获取配置的条件类名称
    for (String[] conditionClasses : getConditionClasses(metadata)) {
        for (String conditionClass : conditionClasses) {
            // 通过反射实例化条件对象
            Condition condition = getCondition(conditionClass, this.context.getClassLoader());
            conditions.add(condition);
        }
    }
    // 条件排序
    AnnotationAwareOrderComparator.sort(conditions);
    return conditions;
}

private List<String[]> getConditionClasses(AnnotatedTypeMetadata metadata) {
    // 获取@Conditional注解的value属性信息
    MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(Conditional.class.getName(), true);
    Object values = (attributes != null ? attributes.get("value") : null);
    return (List<String[]>) (values != null ? values : Collections.emptyList());
}

private Condition getCondition(String conditionClassName, @Nullable ClassLoader classloader) {
    Class<?> conditionClass = ClassUtils.resolveClassName(conditionClassName, classloader);
	// 反射实例化条件对象
    return (Condition) BeanUtils.instantiateClass(conditionClass);
}
```

- Condition接口不用考虑阶段requiredPhase
- ConfigurationCondition接口需要考虑requiredPhase

### 3.2 注册Bean阶段

1. trackedConditionEvaluator是`TrackedConditionEvaluator`，一个内部类，内部也是委派给了`ConditionEvaluator`对象

```java
public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
    TrackedConditionEvaluator trackedConditionEvaluator = new TrackedConditionEvaluator();
    for (ConfigurationClass configClass : configurationModel) {
        loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
    }
}

private void loadBeanDefinitionsForConfigurationClass(
       ConfigurationClass configClass, TrackedConditionEvaluator trackedConditionEvaluator) {

    // 注册阶段：条件注册
    if (trackedConditionEvaluator.shouldSkip(configClass)) {
       String beanName = configClass.getBeanName();
       if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
          this.registry.removeBeanDefinition(beanName);
       }
       this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
       return;
    }

    if (configClass.isImported()) {
       // 注册当前配置类
       registerBeanDefinitionForImportedConfigurationClass(configClass);
    }
    // 遍历方法，解析为BeanDefinition，并注册
    for (BeanMethod beanMethod : configClass.getBeanMethods()) {
       loadBeanDefinitionsForBeanMethod(beanMethod);
    }

    // 加载ImportedResource
    loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
    // 加载ImportedBeanDefinitionRegistrar
    loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
}
```

2. `TrackedConditionEvaluator`增加了一个skipped缓存，将`conditionEvaluator.shouldSkip`的结果缓存起来。

```java
private class TrackedConditionEvaluator {

    private final Map<ConfigurationClass, Boolean> skipped = new HashMap<>();

    public boolean shouldSkip(ConfigurationClass configClass) {
       Boolean skip = this.skipped.get(configClass);
       if (skip == null) {
          if (configClass.isImported()) {
             boolean allSkipped = true;
             for (ConfigurationClass importedBy : configClass.getImportedBy()) {
                if (!shouldSkip(importedBy)) {
                   allSkipped = false;
                   break;
                }
             }
             if (allSkipped) {
                skip = true;
             }
          }
          if (skip == null) {
             skip = conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN);
          }
          this.skipped.put(configClass, skip);
       }
       return skip;
    }
}
```

