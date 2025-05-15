## 1. 概念
> `@ComponentScan`用于批量注册Bean，是在`ConfigurationClassPostProcessor`类中处理的
### 1.1 ComponentScan注解
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
3. 
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

2. ASSIGNABLE_TYPE：指定类的类型

3. ASPECTJ：ASPECTJ表达式方式

4. REGEX：正则表达式方式

5. CUSTOM：自定义过滤器，需要实现`TypeFilter`接口
