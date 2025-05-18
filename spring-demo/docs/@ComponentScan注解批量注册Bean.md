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

> `useDefaultFilters=true`，仅扫描`@Component及其派生注解`



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

> `@ComponentScan`使用`org.springframework.context.annotation.ConfigurationClassPostProcessor`处理， 核心是实现了`BeanDefinitionRegistryPostProcessor`，是一个`BeanFactoryPostProcessor`接口的子类，参与到BeanFactory的生命周期

`ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry`,从注册表中的配置类派生更多的bean定义。

```java
@Override
public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
    processConfigBeanDefinitions(registry);
}

public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
    List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
    String[] candidateNames = registry.getBeanDefinitionNames();

    for (String beanName : candidateNames) {
        BeanDefinition beanDef = registry.getBeanDefinition(beanName);
        if (beanDef.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
            }
        }
        // 检查并设置配置类属性，FULL（代理）和LITE（不代理）。
        else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
            configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
        }
    }

    // Return immediately if no @Configuration classes were found
    if (configCandidates.isEmpty()) {
        return;
    }

    // 排序
    configCandidates.sort((bd1, bd2) -> {
        int i1 = ConfigurationClassUtils.getOrder(bd1.getBeanDefinition());
        int i2 = ConfigurationClassUtils.getOrder(bd2.getBeanDefinition());
        return Integer.compare(i1, i2);
    });

    // Detect any custom bean name generation strategy supplied through the enclosing application context
    SingletonBeanRegistry singletonRegistry = null;
    if (registry instanceof SingletonBeanRegistry sbr) {
        singletonRegistry = sbr;
        if (!this.localBeanNameGeneratorSet) {
            BeanNameGenerator generator = (BeanNameGenerator) singletonRegistry.getSingleton(
                    AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR);
            if (generator != null) {
                this.componentScanBeanNameGenerator = generator;
                this.importBeanNameGenerator = generator;
            }
        }
    }

    if (this.environment == null) {
        this.environment = new StandardEnvironment();
    }

    // Parse each @Configuration class
    ConfigurationClassParser parser = new ConfigurationClassParser(
            this.metadataReaderFactory, this.problemReporter, this.environment,
            this.resourceLoader, this.componentScanBeanNameGenerator, registry);

    // 需要解析的配置类
    Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
    // 已经解析的配置类
    Set<ConfigurationClass> alreadyParsed = CollectionUtils.newHashSet(configCandidates.size());

    /*
     * candidateNames 所有的BeanName -> String
     * candidates 需要解析的配置类 -> BeanDefinitionHolder
     * alreadyParsed 已经解析的候选类 -> ConfigurationClass
     *
     * newCandidateNames 解析后的所有BeanName -> String
     * oldCandidateNames = candidateNames 旧的BeanName -> String
     * alreadyParsedClasses = alreadyParsed 已经解析的配置类ClassName -> String
     *
     * 更新candidates的条件：
     * 		newCandidateNames通过oldCandidateNames过滤 && 配置类 && className不在alreadyParsedClasses
     */

    do {
        StartupStep processConfig = this.applicationStartup.start("spring.context.config-classes.parse");
        // 解析配置类信息
        parser.parse(candidates);
        parser.validate();

        // 更新配置类集合
        Set<ConfigurationClass> configClasses = new LinkedHashSet<>(parser.getConfigurationClasses());
        configClasses.removeAll(alreadyParsed);

        // Read the model and create bean definitions based on its content
        if (this.reader == null) {
            this.reader = new ConfigurationClassBeanDefinitionReader(
                    registry, this.sourceExtractor, this.resourceLoader, this.environment,
                    this.importBeanNameGenerator, parser.getImportRegistry());
        }
        // 根据配置类加载BeanDefinition
        this.reader.loadBeanDefinitions(configClasses);
        // 将所有配置类放到已解析集合
        alreadyParsed.addAll(configClasses);
        processConfig.tag("classCount", () -> String.valueOf(configClasses.size())).end();

        // 清理配置类集合
        candidates.clear();
        // BeanDefinition数量变多了，说明有新注册的BeanDefinition,需要再次解析配置类
        if (registry.getBeanDefinitionCount() > candidateNames.length) {
            // 新的BeanDefinition名称数组
            String[] newCandidateNames = registry.getBeanDefinitionNames();
            // 旧的BeanDefinition名称数组，这个变量是非必须的
            Set<String> oldCandidateNames = Set.of(candidateNames);
            // 已解析配置类的类名数组
            Set<String> alreadyParsedClasses = CollectionUtils.newHashSet(alreadyParsed.size());
            for (ConfigurationClass configurationClass : alreadyParsed) {
                alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
            }
            for (String candidateName : newCandidateNames) {
                // 判断是新注册的Bean
                if (!oldCandidateNames.contains(candidateName)) {
                    BeanDefinition bd = registry.getBeanDefinition(candidateName);
                    // 判断是配置类，并且未解析过的，放入配置类集合中
                    if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) &&
                            !alreadyParsedClasses.contains(bd.getBeanClassName())) {
                        candidates.add(new BeanDefinitionHolder(bd, candidateName));
                    }
                }
            }
            // 更新解析配置类前的Bean名称数组
            candidateNames = newCandidateNames;
        }
    }
    // 存在新的配置类
    while (!candidates.isEmpty());

    // Register the ImportRegistry as a bean in order to support ImportAware @Configuration classes
    if (singletonRegistry != null && !singletonRegistry.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
        singletonRegistry.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
    }

    // Store the PropertySourceDescriptors to contribute them Ahead-of-time if necessary
    this.propertySourceDescriptors = parser.getPropertySourceDescriptors();

    if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory cachingMetadataReaderFactory) {
        // Clear cache in externally provided MetadataReaderFactory; this is a no-op
        // for a shared cache since it'll be cleared by the ApplicationContext.
        cachingMetadataReaderFactory.clearCache();
    }
}
```

`processConfigBeanDefinitions`方法的整体步骤为：

> 从Spring容器中选出所有的配置类 ==> 配置类排序 ==> 解析配置类 ==> 将配置类中新的BeanDefinition注册到Spring容器中 ==>
>
> 从Spring容器中选出新的未被解析过的配置类 ==> 循环解析配置类并注册BeanDefinition，直至没有新的配置类

其中的核心方法有：

- `ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)`，检查bean是否为配置类

  > 从`checkConfigurationClassCandidate`方法中得出，
  >
  > 1. 配置类：有`@Configuration` || beanDefinition的属性`candidate=true` || (不是接口类，且有Component，ComponentScan、Import、ImportSource任一注解 || 有带`@Bean`的方法)
  > 2. `Configuration#proxyBeanMethods`的属性 true时`configurationClass=FULL`，false时`configurationClass=LITE`

```java
static boolean checkConfigurationClassCandidate(
        BeanDefinition beanDef, MetadataReaderFactory metadataReaderFactory) {

    // ignore...

    // 获取Configuration注解的所有属性
    Map<String, Object> config = metadata.getAnnotationAttributes(Configuration.class.getName());
    if (config != null && !Boolean.FALSE.equals(config.get("proxyBeanMethods"))) {
        // proxyBeanMethods = true
        beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, CONFIGURATION_CLASS_FULL);
    }
    else if (config != null || Boolean.TRUE.equals(beanDef.getAttribute(CANDIDATE_ATTRIBUTE)) ||
            isConfigurationCandidate(metadata)) {
        // proxyBeanMethods = false || candidate = true || 是配置类候选
        beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, CONFIGURATION_CLASS_LITE);
    }
    else {
        return false;
    }

    // It's a full or lite configuration candidate... Let's determine the order value, if any.
    Integer order = getOrder(metadata);
    if (order != null) {
        beanDef.setAttribute(ORDER_ATTRIBUTE, order);
    }

    return true;
}

static boolean isConfigurationCandidate(AnnotationMetadata metadata) {
    // Do not consider an interface or an annotation...
    if (metadata.isInterface()) {
        return false;
    }

    // Any of the typical annotations found?
    for (String indicator : candidateIndicators) {
        // 是否标注指定注解 Set.of(
        //			Component.class.getName(),
        //			ComponentScan.class.getName(),
        //			Import.class.getName(),
        //			ImportResource.class.getName());
        if (metadata.isAnnotated(indicator)) {
            return true;
        }
    }

    // 是否有@Bean注解的方法
    return hasBeanMethods(metadata);
}

static boolean hasBeanMethods(AnnotationMetadata metadata) {
    try {
        // 是否有@Bean注解的方法
        return metadata.hasAnnotatedMethods(Bean.class.getName());
    }
    catch (Throwable ex) {
        return false;
    }
}
```

- `parser.parse(candidates);`，使用`ConfigurationClassParser`解析配置类，在`parse`方法中就有处理`@ComponentScan`的逻辑
- `this.reader.loadBeanDefinitions(configClasses);`，加载配置类中定义的BeanDefinition
- `loadBeanDefinitions`后，可能出现新的配置类，所以需要循环处理。

## 3. 源码分析

### 3.1 pase解析配置类

1. 在parse方法中，通过debug发现@Configuration和@Component等配置类走第一个分支` parse(annotatedBeanDef, holder.getBeanName())`

```java
public void parse(Set<BeanDefinitionHolder> configCandidates) {
    for (BeanDefinitionHolder holder : configCandidates) {
        BeanDefinition bd = holder.getBeanDefinition();
        try {
            ConfigurationClass configClass;
            // @Configuration、@ComponentScan定义的配置类走这
            if (bd instanceof AnnotatedBeanDefinition annotatedBeanDef) {
                configClass = parse(annotatedBeanDef, holder.getBeanName());
            }
            else if (bd instanceof AbstractBeanDefinition abstractBeanDef && abstractBeanDef.hasBeanClass()) {
                configClass = parse(abstractBeanDef.getBeanClass(), holder.getBeanName());
            }
            else {
                configClass = parse(bd.getBeanClassName(), holder.getBeanName());
            }

            // Downgrade to lite (no enhancement) in case of no instance-level @Bean methods.
            // 不是抽象类(具体类)， && 不存在非静态方法（全是静态方法） && full ，会降级为lite
            if (!configClass.getMetadata().isAbstract() && !configClass.hasNonStaticBeanMethods() &&
                    ConfigurationClassUtils.CONFIGURATION_CLASS_FULL.equals(
                            bd.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE))) {
                bd.setAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE,
                        ConfigurationClassUtils.CONFIGURATION_CLASS_LITE);
            }
        }
        catch (BeanDefinitionStoreException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanDefinitionStoreException(
                    "Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
        }
    }
    // 最后处理延迟导入类
    this.deferredImportSelectorHandler.process();
}
```

2. 将BeanDefinition包装为`ConfigurationClass`，并调用`processConfigurationClass`方法

```java
private ConfigurationClass parse(AnnotatedBeanDefinition beanDef, String beanName) {
    ConfigurationClass configClass = new ConfigurationClass(
          beanDef.getMetadata(), beanName, (beanDef instanceof ScannedGenericBeanDefinition));
    processConfigurationClass(configClass, DEFAULT_EXCLUSION_FILTER);
    return configClass;
}
```

3. `asSourceClass`方法将配置类包装成`SourceClass`，并调用`doProcessConfigurationClass`方法，解析完成的配置类会被缓存到`configurationClasses`中，

```java
protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) {
    // 条件注入
    if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
       return;
    }

    ConfigurationClass existingClass = this.configurationClasses.get(configClass);
    if (existingClass != null) {
       if (configClass.isImported()) {
          if (existingClass.isImported()) {
             existingClass.mergeImportedBy(configClass);
          }
          // Otherwise ignore new imported config class; existing non-imported class overrides it.
          return;
       }
       else if (configClass.isScanned()) {
          String beanName = configClass.getBeanName();
          if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
             this.registry.removeBeanDefinition(beanName);
          }
          // An implicitly scanned bean definition should not override an explicit import.
          return;
       }
       else {
          // Explicit bean definition found, probably replacing an import.
          // Let's remove the old one and go with the new one.
          this.configurationClasses.remove(configClass);
          removeKnownSuperclass(configClass.getMetadata().getClassName(), false);
       }
    }

    // Recursively process the configuration class and its superclass hierarchy.
    SourceClass sourceClass = null;
    try {
       sourceClass = asSourceClass(configClass, filter);
       do {
          sourceClass = doProcessConfigurationClass(configClass, sourceClass, filter);
       }
       while (sourceClass != null);
    }
    catch (IOException ex) {
       throw new BeanDefinitionStoreException(
             "I/O failure while processing configuration class [" + sourceClass + "]", ex);
    }

    // 存储解析完的配置类
    this.configurationClasses.put(configClass, configClass);
}
```

4. 处理`@ComponentScan`注解的核心方法，获取直接或间接标注@ComponetScan的信息，使用`componentScanParser`解析，如果结果是配置类，需要递归解析

```java
@Nullable
protected final SourceClass doProcessConfigurationClass(
       ConfigurationClass configClass, SourceClass sourceClass, Predicate<String> filter)
       throws IOException {

    // Search for locally declared @ComponentScan annotations first.
    // 直接标注
    Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
          sourceClass.getMetadata(), ComponentScan.class, ComponentScans.class,
          MergedAnnotation::isDirectlyPresent);

    // Fall back to searching for @ComponentScan meta-annotations (which indirectly
    // includes locally declared composed annotations).
    if (componentScans.isEmpty()) {
       // 间接标注
       componentScans = AnnotationConfigUtils.attributesForRepeatable(sourceClass.getMetadata(),
             ComponentScan.class, ComponentScans.class, MergedAnnotation::isMetaPresent);
    }

    if (!componentScans.isEmpty()) {
       List<Condition> registerBeanConditions = collectRegisterBeanConditions(configClass);
       if (!registerBeanConditions.isEmpty()) {
          throw new ApplicationContextException(
                "Component scan for configuration class [%s] could not be used with conditions in REGISTER_BEAN phase: %s"
                      .formatted(configClass.getMetadata().getClassName(), registerBeanConditions));
       }
       for (AnnotationAttributes componentScan : componentScans) {
          // The config class is annotated with @ComponentScan -> perform the scan immediately
          Set<BeanDefinitionHolder> scannedBeanDefinitions =
                this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
          // Check the set of scanned definitions for any further config classes and parse recursively if needed
          for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
             BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
             if (bdCand == null) {
                bdCand = holder.getBeanDefinition();
             }
             // 如果是配置类，则进行递归解析
             if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
                parse(bdCand.getBeanClassName(), holder.getBeanName());
             }
          }
       }
    }

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

5. `@ComponentScan`注解对应一个`ClassPathBeanDefinitionScanner`，将属性全部解析完后，调用`doScan`方法

```java
public Set<BeanDefinitionHolder> parse(AnnotationAttributes componentScan, String declaringClass) {
    // @ComponentScan注解对应ClassPathBeanDefinitionScanner类
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry,
          componentScan.getBoolean("useDefaultFilters"), this.environment, this.resourceLoader);

    Class<? extends BeanNameGenerator> generatorClass = componentScan.getClass("nameGenerator");
    boolean useInheritedGenerator = (BeanNameGenerator.class == generatorClass);
    scanner.setBeanNameGenerator(useInheritedGenerator ? this.beanNameGenerator :
          BeanUtils.instantiateClass(generatorClass));

    ScopedProxyMode scopedProxyMode = componentScan.getEnum("scopedProxy");
    if (scopedProxyMode != ScopedProxyMode.DEFAULT) {
       scanner.setScopedProxyMode(scopedProxyMode);
    }
    else {
       Class<? extends ScopeMetadataResolver> resolverClass = componentScan.getClass("scopeResolver");
       scanner.setScopeMetadataResolver(BeanUtils.instantiateClass(resolverClass));
    }

    scanner.setResourcePattern(componentScan.getString("resourcePattern"));

    for (AnnotationAttributes includeFilterAttributes : componentScan.getAnnotationArray("includeFilters")) {
       List<TypeFilter> typeFilters = TypeFilterUtils.createTypeFiltersFor(includeFilterAttributes, this.environment,
             this.resourceLoader, this.registry);
       for (TypeFilter typeFilter : typeFilters) {
          scanner.addIncludeFilter(typeFilter);
       }
    }
    for (AnnotationAttributes excludeFilterAttributes : componentScan.getAnnotationArray("excludeFilters")) {
       List<TypeFilter> typeFilters = TypeFilterUtils.createTypeFiltersFor(excludeFilterAttributes, this.environment,
          this.resourceLoader, this.registry);
       for (TypeFilter typeFilter : typeFilters) {
          scanner.addExcludeFilter(typeFilter);
       }
    }

    boolean lazyInit = componentScan.getBoolean("lazyInit");
    if (lazyInit) {
       scanner.getBeanDefinitionDefaults().setLazyInit(true);
    }

    Set<String> basePackages = new LinkedHashSet<>();
    String[] basePackagesArray = componentScan.getStringArray("basePackages");
    for (String pkg : basePackagesArray) {
       String[] tokenized = StringUtils.tokenizeToStringArray(this.environment.resolvePlaceholders(pkg),
             ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
       Collections.addAll(basePackages, tokenized);
    }
    for (Class<?> clazz : componentScan.getClassArray("basePackageClasses")) {
       basePackages.add(ClassUtils.getPackageName(clazz));
    }
    if (basePackages.isEmpty()) {
       basePackages.add(ClassUtils.getPackageName(declaringClass));
    }

    scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
       @Override
       protected boolean matchClassName(String className) {
          return declaringClass.equals(className);
       }
    });
    return scanner.doScan(StringUtils.toStringArray(basePackages));
}
```

6. 遍历包扫描路径，获取候选的BeanDefinition，将满足过滤条件，且未注册的Bean注册到Spring容器中

```java
protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Assert.notEmpty(basePackages, "At least one base package must be specified");
    Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
    for (String basePackage : basePackages) {
       Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
       for (BeanDefinition candidate : candidates) {
          ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
          candidate.setScope(scopeMetadata.getScopeName());
          String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
          if (candidate instanceof AbstractBeanDefinition abstractBeanDefinition) {
             postProcessBeanDefinition(abstractBeanDefinition, beanName);
          }
          // 处理一般的注解信息如@Lazy,@Primary,@DependsOn等
          if (candidate instanceof AnnotatedBeanDefinition annotatedBeanDefinition) {
             AnnotationConfigUtils.processCommonDefinitionAnnotations(annotatedBeanDefinition);
          }
          // 未注册过的BeanDefinition
          if (checkCandidate(beanName, candidate)) {
             BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
             definitionHolder =
                   AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
             beanDefinitions.add(definitionHolder);
             // 注册BeanDefinition
             registerBeanDefinition(definitionHolder, this.registry);
          }
       }
    }
    return beanDefinitions;
}
```

7. 找到候选的BeanDefinitions，被@ComponentScan处理的Bean都是`ScannedGenericBeanDefinition`类型的

```java
public Set<BeanDefinition> findCandidateComponents(String basePackage) {
    if (this.componentsIndex != null && indexSupportsIncludeFilters()) {
       return addCandidateComponentsFromIndex(this.componentsIndex, basePackage);
    }
    else {
       return scanCandidateComponents(basePackage);
    }
}

private Set<BeanDefinition> scanCandidateComponents(String basePackage) {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();
    try {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + '/' + this.resourcePattern;
        Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
        boolean traceEnabled = logger.isTraceEnabled();
        boolean debugEnabled = logger.isDebugEnabled();
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            if (filename != null && filename.contains(ClassUtils.CGLIB_CLASS_SEPARATOR)) {
                // Ignore CGLIB-generated classes in the classpath
                continue;
            }
            try {
                MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                if (isCandidateComponent(metadataReader)) {
                    // 创建ScannedGenericBeanDefinition
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                    sbd.setSource(resource);
                    if (isCandidateComponent(sbd)) {
                        candidates.add(sbd);
                    }
                }
            }
        }
    }
    return candidates;
}

protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
    for (TypeFilter tf : this.excludeFilters) {
        if (tf.match(metadataReader, getMetadataReaderFactory())) {
            return false;
        }
    }
    for (TypeFilter tf : this.includeFilters) {
        if (tf.match(metadataReader, getMetadataReaderFactory())) {
            // 条件注入
            return isConditionMatch(metadataReader);
        }
    }
    return false;
}
```

### 3.2 loadBeanDefinitions加载BeanDefinition

1. 循环遍历配置类

```java
public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
    TrackedConditionEvaluator trackedConditionEvaluator = new TrackedConditionEvaluator();
    for (ConfigurationClass configClass : configurationModel) {
       loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
    }
}
```

2. 注册当前配置类，以及遍历注册@Bean方法

```java
private void loadBeanDefinitionsForConfigurationClass(
       ConfigurationClass configClass, TrackedConditionEvaluator trackedConditionEvaluator) {

    // 条件注册
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

3. 注册配置类，对应的bean是`AnnotatedGenericBeanDefinition`类的

```java
private void registerBeanDefinitionForImportedConfigurationClass(ConfigurationClass configClass) {
    AnnotationMetadata metadata = configClass.getMetadata();
    AnnotatedGenericBeanDefinition configBeanDef = new AnnotatedGenericBeanDefinition(metadata);

    ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(configBeanDef);
    configBeanDef.setScope(scopeMetadata.getScopeName());
    String configBeanName = this.importBeanNameGenerator.generateBeanName(configBeanDef, this.registry);
    AnnotationConfigUtils.processCommonDefinitionAnnotations(configBeanDef, metadata);

    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(configBeanDef, configBeanName);
    definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
    this.registry.registerBeanDefinition(definitionHolder.getBeanName(), definitionHolder.getBeanDefinition());
    configClass.setBeanName(configBeanName);
}
```

4. 解析@Bean属性，创建`ConfigurationClassBeanDefinition`，并注册到Spring容器中

```java
private void loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod) {
    ConfigurationClass configClass = beanMethod.getConfigurationClass();
    MethodMetadata metadata = beanMethod.getMetadata();
    String methodName = metadata.getMethodName();

    // Do we need to mark the bean as skipped by its condition?
    if (this.conditionEvaluator.shouldSkip(metadata, ConfigurationPhase.REGISTER_BEAN)) {
       configClass.skippedBeanMethods.add(methodName);
       return;
    }
    if (configClass.skippedBeanMethods.contains(methodName)) {
       return;
    }

    AnnotationAttributes bean = AnnotationConfigUtils.attributesFor(metadata, Bean.class);
    Assert.state(bean != null, "No @Bean annotation attributes");

    // Consider name and any aliases
    List<String> names = new ArrayList<>(Arrays.asList(bean.getStringArray("name")));
    String beanName = (!names.isEmpty() ? names.remove(0) : methodName);

    // Register aliases even when overridden
    for (String alias : names) {
       this.registry.registerAlias(beanName, alias);
    }

    // Has this effectively been overridden before (for example, via XML)?
    if (isOverriddenByExistingDefinition(beanMethod, beanName)) {
       if (beanName.equals(beanMethod.getConfigurationClass().getBeanName())) {
          throw new BeanDefinitionStoreException(beanMethod.getConfigurationClass().getResource().getDescription(),
                beanName, "Bean name derived from @Bean method '" + beanMethod.getMetadata().getMethodName() +
                "' clashes with bean name for containing configuration class; please make those names unique!");
       }
       return;
    }

    // @Bean标注的方法创建ConfigurationClassBeanDefinition
    ConfigurationClassBeanDefinition beanDef = new ConfigurationClassBeanDefinition(configClass, metadata, beanName);
    beanDef.setSource(this.sourceExtractor.extractSource(metadata, configClass.getResource()));

    if (metadata.isStatic()) {
       // static @Bean method
       if (configClass.getMetadata() instanceof StandardAnnotationMetadata sam) {
          beanDef.setBeanClass(sam.getIntrospectedClass());
       }
       else {
          beanDef.setBeanClassName(configClass.getMetadata().getClassName());
       }
       beanDef.setUniqueFactoryMethodName(methodName);
    }
    else {
       // instance @Bean method
       beanDef.setFactoryBeanName(configClass.getBeanName());
       beanDef.setUniqueFactoryMethodName(methodName);
    }

    if (metadata instanceof StandardMethodMetadata smm &&
          configClass.getMetadata() instanceof StandardAnnotationMetadata sam) {
       Method method = ClassUtils.getMostSpecificMethod(smm.getIntrospectedMethod(), sam.getIntrospectedClass());
       if (method == smm.getIntrospectedMethod()) {
          beanDef.setResolvedFactoryMethod(method);
       }
    }

    beanDef.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
    AnnotationConfigUtils.processCommonDefinitionAnnotations(beanDef, metadata);

    boolean autowireCandidate = bean.getBoolean("autowireCandidate");
    if (!autowireCandidate) {
       beanDef.setAutowireCandidate(false);
    }

    boolean defaultCandidate = bean.getBoolean("defaultCandidate");
    if (!defaultCandidate) {
       beanDef.setDefaultCandidate(false);
    }

    Bean.Bootstrap instantiation = bean.getEnum("bootstrap");
    if (instantiation == Bean.Bootstrap.BACKGROUND) {
       beanDef.setBackgroundInit(true);
    }

    String initMethodName = bean.getString("initMethod");
    if (StringUtils.hasText(initMethodName)) {
       beanDef.setInitMethodName(initMethodName);
    }

    String destroyMethodName = bean.getString("destroyMethod");
    beanDef.setDestroyMethodName(destroyMethodName);

    // Consider scoping
    ScopedProxyMode proxyMode = ScopedProxyMode.NO;
    AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(metadata, Scope.class);
    if (attributes != null) {
       beanDef.setScope(attributes.getString("value"));
       proxyMode = attributes.getEnum("proxyMode");
       if (proxyMode == ScopedProxyMode.DEFAULT) {
          proxyMode = ScopedProxyMode.NO;
       }
    }

    // Replace the original bean definition with the target one, if necessary
    BeanDefinition beanDefToRegister = beanDef;
    if (proxyMode != ScopedProxyMode.NO) {
       BeanDefinitionHolder proxyDef = ScopedProxyCreator.createScopedProxy(
             new BeanDefinitionHolder(beanDef, beanName), this.registry,
             proxyMode == ScopedProxyMode.TARGET_CLASS);
       beanDefToRegister = new ConfigurationClassBeanDefinition(
             (RootBeanDefinition) proxyDef.getBeanDefinition(), configClass, metadata, beanName);
    }

    // 注册含有@Bean的方法
    this.registry.registerBeanDefinition(beanName, beanDefToRegister);
}
```

## 4. 结论

### 4.1 流程图



### 4.2 哪些是配置类

1. @Configuration注解的类
2. @Component注解的类
3. @ComponentScan注解的类
4. @Import注解的类
5. @ImportSource注解的类
6. 有@Bean方法的类
7. Beandefinition的candidate属性为true的类
