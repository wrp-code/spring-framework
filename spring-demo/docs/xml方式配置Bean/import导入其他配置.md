## 是什么

> `<import>`可以在xml配置文件中导入其他的xml配置文件。主要作用是拆分配置文件，将不同的配置信息放在不同的配置文件中，如数据库相关的配置放在db.xml中、redis连接的配置放在redis.xml中。

## 测试

```java
public class User {}

public class ImportXmlTest {

    @Test
    public void test() {
       String xml = "classpath:/framework/beandefinition/import-beans.xml";
       var beanFactory = new DefaultListableBeanFactory();
       XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
       int beanCount = reader.loadBeanDefinitions(xml);
       Assertions.assertEquals(3, beanCount);
       Assertions.assertNotNull(beanFactory.getBean("user-import"));
       Assertions.assertNotNull(beanFactory.getBean("user-a"));
       Assertions.assertNotNull(beanFactory.getBean("user-b"));
       Assertions.assertThrowsExactly(NoSuchBeanDefinitionException.class,
             () -> beanFactory.getBean("user-c"));
    }
}
```

import-beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

	<bean id="user-import" class="com.wrp.spring.framework.User"/>

	<import resource="classpath:framework/beandefinition/import-a.xml"/>
	<import resource="classpath:framework/beandefinition/import-b.xml"/>
</beans>
```

import-a.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

    <bean id="user-a" class="com.wrp.spring.framework.User"/>
</beans>
```

import-b.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

    <bean id="user-b" class="com.wrp.spring.framework.User"/>
</beans>
```

