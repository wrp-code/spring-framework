## 1. Spring IOC容器
> 本质上就是对象创建过程的一个封装，实例化、初始化，在这两个阶段的前后增加一些扩展点。
> 

## 2. 元信息阶段
> 元信息在Spring容器中对应的BeanDefinition，此阶段主要包括元信息的配置、元信息的解析、元信息的注册等步骤
>
细分如下阶段：

1. BeanDefinition配置阶段
2. BeanDefinition解析阶段
3. BeanDefinition注册阶段
4. BeanDefinition合并阶段
5. Bean Class类加载阶段

### 2.1 BeanDefinition配置阶段

#### 2.1.1 XMl方式

已过时

#### 2.1.2 API方式

`BeanDefinitionBuilder`工具类构建`BeanDefinition`

#### 2.1.3 注解方式(推荐)

1. `@Configuration` + `@Bean`
2. `@ComponentScan` + `@Component`
3. `@Import`

#### 2.1.4 Properties方式

> 用的不多，与xml方式类似

### 2.2 BeanDefinition解析阶段

#### 2.2.1 xml方式解析

`XmlBeanDefinitionReader`

```java
    //定义一个spring容器，这个容器默认实现了BeanDefinitionRegistry，所以本身就是一个bean注册器
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

    //定义一个xml的BeanDefinition读取器，需要传递一个BeanDefinitionRegistry（bean注册器）对象
    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);

    //指定bean xml配置文件的位置
    String location = "classpath:/com/javacode2018/lesson002/demo2/beans.xml";
    //通过XmlBeanDefinitionReader加载bean xml文件，然后将解析产生的BeanDefinition注册到容器容器中
    int countBean = xmlBeanDefinitionReader.loadBeanDefinitions(location);
```

#### 2.2.2 properties方式解析

`PropertiesBeanDefinitionReader`

#### 2.2.3 注解方式解析

`AnnotatedBeanDefinitionReader`

```java
	//定义一个spring容器，这个容器默认实现了BeanDefinitionRegistry，所以本身就是一个bean注册器
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

    //定义一个注解方式的BeanDefinition读取器，需要传递一个BeanDefinitionRegistry（bean注册器）对象
    AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(factory);

    //通过PropertiesBeanDefinitionReader加载bean properties文件，然后将解析产生的BeanDefinition注册到容器容器中
    annotatedBeanDefinitionReader.register(Service1.class, Service2.class);
```

### 2.3 BeanDefinition注册阶段

`BeanDefinitionRegistry`，接口的唯一实现`org.springframework.beans.factory.support.DefaultListableBeanFactory`

### 2.4 BeanDefinition合并阶段

因为定义的Bean可能存在父子关系，需要递归合并属性，才得到完整的信息

`org.springframework.beans.factory.support.AbstractBeanFactory#getMergedBeanDefinition`

### 2.5 Bean Class 类加载阶段

`BeanDefinition`有个`beanClass`属性，他可能是字符串，需用`ClassLoader`加载为`Class `

`org.springframework.beans.factory.support.AbstractBeanFactory#resolveBeanClass`

## 3. Bean实例化阶段

> 实例化阶段的核心逻辑是使用反射创建对象。
> 
细分如下阶段：
1. Bean实例化前阶段
2. Bean实例化阶段
3. 合并后的BeanDefinition处理
4. 实例化后阶段



### 3.1 Bean实例化前阶段

实例化前，调用`InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation`

若，返回结果不为空，就会跳过Spring后续的实例化过程（但是会执行`BeanPostProcessor#postProcessAfterInitialization`操作）

```java
	protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
		Object bean = null;
		if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
			if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
				Class<?> targetType = determineTargetType(beanName, mbd);
				if (targetType != null) {
					// 实例化前，如果返回了对象，就不再走Spring的实例化了
					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
					if (bean != null) {
						// 初始化后操作
						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
					}
				}
			}
			mbd.beforeInstantiationResolved = (bean != null);
		}
		return bean;
	}

	protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
		for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
			Object result = bp.postProcessBeforeInstantiation(beanClass, beanName);
			// 如果result != null ，将直接跳过Spring的实例化过程
			if (result != null) {
				return result;
			}
		}
		return null;
	}
```



### 3.2 Bean实例化阶段

如果设置了工厂方法，直接反射调用工厂方法创建Bean，反之

探测构造器，利用构造器反射创建Bean，探测构造器使用`SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors`

比较重要的实现类为：`AutowiredAnnotationBeanPostProcessor`，将标注`@Autowired`注解的构造器作为候选

```java
	protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {

		// 工厂方法不为空，静态工厂方法或者实例工厂方法创建bean
		if (mbd.getFactoryMethodName() != null) {
			return instantiateUsingFactoryMethod(beanName, mbd, args);
		}

		// Candidate constructors for autowiring?
		// 探测构造器钩子
		Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
		if (ctors != null || mbd.getResolvedAutowireMode() == AUTOWIRE_CONSTRUCTOR ||
				mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
			// 有候选构造器 || 构造器自动注入模式 || 有构造器参数 || args不为空
			return autowireConstructor(beanName, mbd, ctors, args);
		}


		// 使用无参构造
		return instantiateBean(beanName, mbd);
	}

	protected Constructor<?>[] determineConstructorsFromBeanPostProcessors(@Nullable Class<?> beanClass, String beanName)
			throws BeansException {

		if (beanClass != null && hasInstantiationAwareBeanPostProcessors()) {
			for (SmartInstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().smartInstantiationAware) {
				Constructor<?>[] ctors = bp.determineCandidateConstructors(beanClass, beanName);
				if (ctors != null) {
					return ctors;
				}
			}
		}
		return null;
	}
```



### 3.3 应用MergedBeanDefinitionPostProcessor

调用`MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition`

两个比较重要的实现类

`org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor`
在 postProcessMergedBeanDefinition 方法中对 `@Autowired`、`@Value` 标注的方法、字段进行缓存

`org.springframework.context.annotation.CommonAnnotationBeanPostProcessor`
在 postProcessMergedBeanDefinition 方法中对 `@Resource` 标注的字段、`@Resource` 标注的方法、 `@PostConstruct` 标注的字段、 `@PreDestroy`标注的方法进行缓存

```java
protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName) {
    for (MergedBeanDefinitionPostProcessor processor : getBeanPostProcessorCache().mergedDefinition) {
       processor.postProcessMergedBeanDefinition(mbd, beanType, beanName);
    }
}
```



### 3.4 实例化后阶段

调用`InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation`，在方法内部可以自定义属性赋值

返回false时会跳过Spring的赋值阶段。

```java
if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
    for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
       
       if (!bp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
          return;
       }
    }
}
```



## 4. Bean属性赋值阶段

> 通过反射给字段赋值
> 
1. Bean属性赋值前阶段
2. Bean属性赋值阶段

### 4.1 Bean属性赋值前阶段

解析自动注入的属性信息，并调用`InstantiationAwareBeanPostProcessor#postProcessProperties`



```java
PropertyValues pvs = (mbd.hasPropertyValues() ? mbd.getPropertyValues() : null);

// 解析自定注入的参数
int resolvedAutowireMode = mbd.getResolvedAutowireMode();
if (resolvedAutowireMode == AUTOWIRE_BY_NAME || resolvedAutowireMode == AUTOWIRE_BY_TYPE) {
    MutablePropertyValues newPvs = new MutablePropertyValues(pvs);
    // Add property values based on autowire by name if applicable.
    if (resolvedAutowireMode == AUTOWIRE_BY_NAME) {
       autowireByName(beanName, mbd, bw, newPvs);
    }
    // Add property values based on autowire by type if applicable.
    if (resolvedAutowireMode == AUTOWIRE_BY_TYPE) {
       autowireByType(beanName, mbd, bw, newPvs);
    }
    pvs = newPvs;
}

// 阶段8.2：Bean属性设置阶段 > 赋值前阶段
//AutowiredAnnotationBeanPostProcessor在这个方法中对@Autowired、@Value标注的字段、方法注入值。
//CommonAnnotationBeanPostProcessor在这个方法中对@Resource标注的字段和方法注入值。
if (hasInstantiationAwareBeanPostProcessors()) {
    if (pvs == null) {
       pvs = mbd.getPropertyValues();
    }
    for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
       // 返回空时，直接跳过属性赋值阶段
       // 这里可以对属性值进行填充
       PropertyValues pvsToUse = bp.postProcessProperties(pvs, bw.getWrappedInstance(), beanName);
       if (pvsToUse == null) {
          return;
       }
       pvs = pvsToUse;
    }
}
```

### 4.2 Bean属性赋值阶段





## 5. Bean初始化阶段

> 调用一系列的回调接口
1. Bean Aware接口回调阶段
2. Bean初始化前阶段
3. Bean初始化阶段
4. Bean初始化后阶段
5. 所有单例Bean初始化完成后阶段

## 6. Bean使用阶段
> 从Spring容器中获取Bean对象
> 

## 7. Bean销毁阶段
> Bean使用完后，需要对其资源进行释放
1. Bean销毁前阶段
2. Bean销毁阶段
