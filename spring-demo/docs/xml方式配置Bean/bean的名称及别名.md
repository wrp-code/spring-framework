## 是什么

> Spring容器中，名称作为Bean的唯一标识。Bean在不同的应用场景中可以有不同的名称（别名）

## 1. 单元测试

```java
public class User { }

public class BeanOfXMLTest {

	@Test
	public void test() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:framework/beandefinition/beandefinition.xml");
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			String[] aliases = beanFactory.getAliases(beanName);
			System.out.printf("beanName: %s, aliases: %s \n",beanName, Arrays.asList(aliases));
		}
	}
}
```

beandefinition.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

	<bean id="user1" class="com.wrp.spring.framework.beandefinition.User"/>
	<bean name="user2" class="com.wrp.spring.framework.beandefinition.User"/>
	<bean class="com.wrp.spring.framework.beandefinition.User"/>
	<bean id="user3,user4" class="com.wrp.spring.framework.beandefinition.User"/>
	<bean name="user4,user5;user6" class="com.wrp.spring.framework.beandefinition.User"/>
	<bean id="user7" name="user8 user9" class="com.wrp.spring.framework.beandefinition.User"/>

	<alias name="user1" alias="alias1"/>
	<alias name="user7" alias="alias2"/>
</beans>
```

结果

```tex
beanName: user1, aliases: [alias1] 
beanName: user2, aliases: [] 
beanName: com.wrp.spring.framework.beandefinition.User#0, aliases: [com.wrp.spring.framework.beandefinition.User] 
beanName: user3,user4, aliases: [] 
beanName: user4, aliases: [user5, user6] 
beanName: user7, aliases: [user9, user8, alias2] 
```

结论

1. id会作为一个整体被解析
2. name可以设置多个值，用 `,`、`;`、` `分割
3. id和name不全为空时，第一个值设置为beanName，后面的为别名
4. 不设置id和name时，spring会生成默认的名称（全限定类名#序号）和别名（全限定类名）

## 2. 原理

> `XmlBeanDefinitionReader#loadBeanDefinitions`最终会调用`org.springframework.beans.factory.xml.BeanDefinitionParserDelegate#parseBeanDefinitionElement`

```java
public static final String MULTI_VALUE_ATTRIBUTE_DELIMITERS = ",; ";

public BeanDefinitionHolder parseBeanDefinitionElement(Element ele, @Nullable BeanDefinition containingBean) {
    // 获取id
    String id = ele.getAttribute(ID_ATTRIBUTE);
    // 获取name
    String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);

    List<String> aliases = new ArrayList<>();
    if (StringUtils.hasLength(nameAttr)) {
        // 分割name
        String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, MULTI_VALUE_ATTRIBUTE_DELIMITERS);
        aliases.addAll(Arrays.asList(nameArr));
    }

    String beanName = id;
    // 如果id没有设置，并且name设置了，将name分割结果的第一个值给beanName
    if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
        beanName = aliases.remove(0);
    }

    // 返回的是GenericBeanDefinition类型的值，因为 new GenericBeanDefinition();
    AbstractBeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName, containingBean);
    if (beanDefinition != null) {
        // id和name都没有设置
        if (!StringUtils.hasText(beanName)) {
            try {
                if (containingBean != null) {
                    // 属性内部bean，全限定类名#identityHexString
                    beanName = BeanDefinitionReaderUtils.generateBeanName(
                            beanDefinition, this.readerContext.getRegistry(), true);
                }
                else {
                    // 底层调用org.springframework.beans.factory.support.BeanNameGenerator#generateBeanName
                    beanName = this.readerContext.generateBeanName(beanDefinition);
                    String beanClassName = beanDefinition.getBeanClassName();
                    // 当全限定类名没有被使用过时，才将全限定类名设置为别名
                    if (beanClassName != null &&
                            beanName.startsWith(beanClassName) && beanName.length() > beanClassName.length() &&
                            !this.readerContext.getRegistry().isBeanNameInUse(beanClassName)) {
                        aliases.add(beanClassName);
                    }
                }
            }
            catch (Exception ex) {
                error(ex.getMessage(), ele);
                return null;
            }
        }
        String[] aliasesArray = StringUtils.toStringArray(aliases);
        return new BeanDefinitionHolder(beanDefinition, beanName, aliasesArray);
    }

    return null;
}
```

## 3. BeanNameGenerator

> 当id和name都没有设置时，会通过`BeanNameGenerator`自动生成beanName

```java
public interface BeanNameGenerator {

	String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
```

有个默认实现

```java
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    // 饿汉式
	public static final DefaultBeanNameGenerator INSTANCE = new DefaultBeanNameGenerator();

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
	}

}
```

委派`BeanDefinitionReaderUtils.generateBeanName`，最终调用`uniqueBeanName`

```java
public static final String GENERATED_BEAN_NAME_SEPARATOR = "#"
public static String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
    String id = beanName;
    int counter = -1;

    String prefix = beanName + GENERATED_BEAN_NAME_SEPARATOR;
    while (counter == -1 || registry.containsBeanDefinition(id)) {
        counter++;
        id = prefix + counter;
    }
    return id;
}
```

生成beanName的默认策略是`全限定类名 + # + 从0开始的自增序列`
