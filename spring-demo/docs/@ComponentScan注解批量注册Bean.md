## 0. 概念
> `@ComponentScan`用于批量注册Bean，是在`org.springframework.context.annotation.ConfigurationClassPostProcessor`类中处理，**递归扫描指定包中的所有类，将满足条件的类批量注册到spring容器中**
### 0.1 ComponentScan注解
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    
	@AliasFor("basePackages")
	String[] value() default {};
    
	@AliasFor("value")
	String[] basePackages() default {};
    
	Class<?>[] basePackageClasses() default {};
    
	Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
    
	Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;
    
	ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;
    
	// 需要扫描包中的那些资源，默认是：**/*.class
	String resourcePattern() default ClassPathScanningCandidateComponentProvider.DEFAULT_RESOURCE_PATTERN;
    
	// 凡是类上有@Repository、@Service、@Controller、@Component这几个注解中的任何一个的，
	// 那么这个类就会被作为bean注册到spring容器中
	boolean useDefaultFilters() default true;

	/**
	 * 自定义扫描哪些类
	 * 多个Filter之间是 or 关系
	 */
	Filter[] includeFilters() default {};

	/**
	 * 排除哪些类不被扫描
	 */
	Filter[] excludeFilters() default {};
    
	boolean lazyInit() default false;
}
```
1. value、basePackages、basePackageClasses 三个属性指定扫描哪些包，默认将@ComponentScan修饰的类所在的包作为扫描包
2. useDefaultFilters、includeFilters、excludeFilters 三个属性控制过滤器，默认useDefaultFilters=true，
会将类上有@Repository、@Service、@Controller、@Component注解的类注入到Spring容器
3. @ComponentScan是可以重复使用的
## 1. 使用方法
### 1.1 仅标注@ComponentScan

### 1.2 指定包路径属性
> 利用value、basePackages属性，二选一

### 1.3 指定类，扫描类所在的包
> 利用basePackageClasses属性
> 

### 1.4 自定义过滤规则
> includeFilters 自定义扫描的过滤规则，需要用到Filter注解类
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@interface Filter {
    
    FilterType type() default FilterType.ANNOTATION;

    /**
     * 对应：ANNOTATION、ASSIGNABLE_TYPE、CUSTOM三种type
     */
    @AliasFor("classes")
    Class<?>[] value() default {};

    @AliasFor("value")
    Class<?>[] classes() default {};

    /**
     * 对应ASPECTJ、REGEX两种类型
     */
    String[] pattern() default {};

}
```
过滤规则有5种：
1. ANNOTATION：自定义注解方式，同@Component

2. ASSIGNABLE_TYPE：指定类的类型，满足`Class.isAssignableFrom`

3. ASPECTJ：ASPECTJ表达式方式

4. REGEX：正则表达式方式

5. CUSTOM：自定义过滤器，需要实现`TypeFilter`接口

#### 1.4.1 自定义注解方式

> 自定义注解 + 默认的注解同时生效

方式1，此方式同`@Service`等注解，使用默认的处理行为

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {

	@AliasFor(annotation = Component.class)
	String value() default  "";
}

@ComponentScan
public class Config2 {}
```

方式2，此方式需要在`includeFilters`属性中指定注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
}

@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = MyComponent.class)
})
public class Config {
}
```

#### 1.4.2 指定类的类型

> 需要满足`IService.class.isAssignableFrom()`关系

```java
@ComponentScan(includeFilters = {
       @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
          value = IService.class)
})
public class Config3 {
}
```

#### 1.4.3 自定义过滤器

> 自定义过滤器需要实现`TypeFilter`接口

```java
@FunctionalInterface
public interface TypeFilter {

    // metadataReader 可以获取到类信息及类上的注解信息
    // MetadataReaderFactory 是MetadataReader的简单工厂类
	boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException;

}
```

在`TypeFilter`实现类中写自己的匹配原则

```java
public class MyTypeFilter implements TypeFilter {
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		String className = metadataReader.getClassMetadata().getClassName();
		try {
			return Class.forName(className).isAssignableFrom(IService.class);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}

@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.CUSTOM,
			value = MyTypeFilter.class)
})
public class Config4 {
}
```

### 1.5 重复使用

```java
@ComponentScan
@ComponentScan
public class Config2 {
}
```

实验发现，重复扫描同一个类，Bean只会被注册一次



## 2. 核心原理

> @ComponentScan使用`org.springframework.context.annotation.ConfigurationClassPostProcessor`处理， 核心是实现了`BeanDefinitionRegistryPostProcessor`，是一个`BeanFactoryPostProcessor`接口的子类，参与到BeanFactory的生命周期





## 3. 源码分析



