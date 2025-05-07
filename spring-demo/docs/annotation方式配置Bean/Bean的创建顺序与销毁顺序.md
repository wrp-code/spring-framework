## 1. 无@DependsOn

### 1.1 @Configuration + @Bean

```java
public class A implements DisposableBean {

	public A() {
		System.out.println("Create A...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy A...");
	}
}

public class B implements DisposableBean {

	public B() {
		System.out.println("Create B...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy B...");
	}
}


public class C implements DisposableBean {

	public C() {
		System.out.println("Create C...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy C...");
	}
}
```

定义配置类

```java
@Configuration
public class Config1 {

	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}

	@Bean
	public C c() {
		return new C();
	}
}
```

单元测试

```java
@Test
public void test7() {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Config1.class);
    context.close();
}
```

结论1：Bean的创建顺序和@Bean方法的声明顺序相同，Bean的销毁顺序与创建顺序相反。

```tex
Create A...
Create B...
Create C...
Destroy C...
Destroy B...
Destroy A...
```

### 1.2 @ComponentScan + @Component

> @Repository、@Service、@Controller等注解均派生于@Component，故仅测试@Componet

结论2：同结论1

## 2. 有@DependsOn

### 测试demo1

```java
@Configuration
public class Config3 {

	@DependsOn({"c","d"})
	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}

	@DependsOn("d")
	@Bean
	public C c() {
		return new C();
	}

	@Bean
	public D d() {
		return new D();
	}
}
```

结论2：Bean创建时会优先创建@DependsOn指定的Bean。此案例中，根据默认顺序优先创建a，由于a指定了`@DependsOn({"c","d"})`，先去创建c和d，创建c时指定了`@DependsOn("d")`，故最先创建d，再创建c，最后创建a，最终根据默认顺序创建剩余的b。

```tex
Create D...
Create C...
Create A...
Create B...
Destroy B...
Destroy A...
Destroy C...
Destroy D...
```

### 测试demo2

```java
@Configuration
public class Config4 {

	@DependsOn({"c","b"})
	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}

	@Bean
	public C c() {
		return new C();
	}

}
```

结论3：结合测试demo2，@DependsOn中指定的Bean名称顺序也会影响Bean创建顺序。

```tex
Create C...
Create B...
Create A...
Destroy A...
Destroy B...
Destroy C...
```

### 测试demo3

```java
@Configuration
public class Config5 {

	@DependsOn({"c","b","d"})
	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}

	@Bean
	public C c() {
		return new C();
	}

}
```

结论4：@DependsOn中指定未定义的Bean名称，会导致`NoSuchBeanDefinitionException`

```tex
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'd' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:931)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1350)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:266)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:163)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:279)
```



## 结论

1. 默认Bean创建顺序与声明顺序相同
2. Bean创建顺序与销毁顺序相反
3. 创建Bean时，优先创建`@DependsOn`中指定的Bean
4. `@DependsOn`必须指定定义了的Bean名称



## 原理

### Bean创建顺序与声明顺序相同

Spring在`org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry`中解析配置类

```java
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {

		processConfigBeanDefinitions(registry);
	}

	public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
		List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
		String[] candidateNames = registry.getBeanDefinitionNames();

		for (String beanName : candidateNames) {
			BeanDefinition beanDef = registry.getBeanDefinition(beanName);
			// 检查并设置配置类属性，FULL（代理）和LITE（不代理）。
			else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
				configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
			}
		}

		// Return immediately if no @Configuration classes were found
		if (configCandidates.isEmpty()) {
			return;
		}

		// Parse each @Configuration class
		ConfigurationClassParser parser = new ConfigurationClassParser(
				this.metadataReaderFactory, this.problemReporter, this.environment,
				this.resourceLoader, this.componentScanBeanNameGenerator, registry);

		Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
		Set<ConfigurationClass> alreadyParsed = CollectionUtils.newHashSet(configCandidates.size());
		do {
			StartupStep processConfig = this.applicationStartup.start("spring.context.config-classes.parse");
			// 解析配置类信息
			parser.parse(candidates);
			parser.validate();

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
			alreadyParsed.addAll(configClasses);
			processConfig.tag("classCount", () -> String.valueOf(configClasses.size())).end();

			candidates.clear();
			if (registry.getBeanDefinitionCount() > candidateNames.length) {
				String[] newCandidateNames = registry.getBeanDefinitionNames();
				Set<String> oldCandidateNames = Set.of(candidateNames);
				Set<String> alreadyParsedClasses = CollectionUtils.newHashSet(alreadyParsed.size());
				for (ConfigurationClass configurationClass : alreadyParsed) {
					alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
				}
				for (String candidateName : newCandidateNames) {
					if (!oldCandidateNames.contains(candidateName)) {
						BeanDefinition bd = registry.getBeanDefinition(candidateName);
						if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) &&
								!alreadyParsedClasses.contains(bd.getBeanClassName())) {
							candidates.add(new BeanDefinitionHolder(bd, candidateName));
						}
					}
				}
				candidateNames = newCandidateNames;
			}
		}
		while (!candidates.isEmpty());
	}
```

核心类`ConfigurationClassParser`对配置类进行解析、BeanDefinition的解析和加载

解析过程中，根据class文件从上往下解析为`ConfigurationClass`

```java
	public void parse(Set<BeanDefinitionHolder> configCandidates) {
		for (BeanDefinitionHolder holder : configCandidates) {
			BeanDefinition bd = holder.getBeanDefinition();
			try {
				ConfigurationClass configClass;
                // @Configuration
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

		this.deferredImportSelectorHandler.process();
	}
```

加载BeanDefinition阶段，遍历`ConfigurationClass`集合，遍历方法解析并加载BeanDefinition

```java
	public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
		TrackedConditionEvaluator trackedConditionEvaluator = new TrackedConditionEvaluator();
		for (ConfigurationClass configClass : configurationModel) {
			loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
		}
	}

	private void loadBeanDefinitionsForConfigurationClass(
			ConfigurationClass configClass, TrackedConditionEvaluator trackedConditionEvaluator) {

		if (trackedConditionEvaluator.shouldSkip(configClass)) {
			String beanName = configClass.getBeanName();
			if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
				this.registry.removeBeanDefinition(beanName);
			}
			this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
			return;
		}

		if (configClass.isImported()) {
			registerBeanDefinitionForImportedConfigurationClass(configClass);
		}
        // 遍历方法，解析为BeanDefinition，并注册
		for (BeanMethod beanMethod : configClass.getBeanMethods()) {
			loadBeanDefinitionsForBeanMethod(beanMethod);
		}

		loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
		loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
	}
```



### Bean创建顺序与销毁顺序相反

`ApplicationContext.close`方法会销毁单例Bean，最终会调用`destroySingletons`

会按照`disposableBeans`集合倒序执行销毁方法。Bean会在创建完成后加入到`disposableBeans`中

```java
	public void destroySingletons() {
		this.singletonsCurrentlyInDestruction = true;

		String[] disposableBeanNames;
		synchronized (this.disposableBeans) {
			disposableBeanNames = StringUtils.toStringArray(this.disposableBeans.keySet());
		}
        // 按照disposableBeans倒序执行
		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			destroySingleton(disposableBeanNames[i]);
		}

		this.containedBeanMap.clear();
		this.dependentBeanMap.clear();
		this.dependenciesForBeanMap.clear();

		this.singletonLock.lock();
		try {
			clearSingletonCache();
		}
		finally {
			this.singletonLock.unlock();
		}
	}
```



### @DependsOn如何生效的

@DependsOn对应BeanDefinition中的属性，在getBean时检查dependsOn属性，创建当前Bean前调用dependsOn中的Bean。

```java
	protected <T> T doGetBean(
			String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
			throws BeansException {
        String[] dependsOn = mbd.getDependsOn();
				if (dependsOn != null) {
					for (String dep : dependsOn) {
						if (isDependent(beanName, dep)) {
							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
									"Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
						}
						registerDependentBean(dep, beanName);
						try {
							getBean(dep);
						}
					}
				}
	}
```

