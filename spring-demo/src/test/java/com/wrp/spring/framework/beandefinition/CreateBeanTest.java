package com.wrp.spring.framework.beandefinition;

import com.wrp.spring.framework.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author wrp
 * @since 2025年05月04日 14:39
 */
public class CreateBeanTest {

	@Test
	public void test1() {
		String xml = "classpath:framework/beandefinition/constructor-createbean.xml";
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		int beanCount = reader.loadBeanDefinitions(xml);
		Assertions.assertEquals(2, beanCount);
		Assertions.assertNull(beanFactory.getBean("user1", User.class).getName());
		Assertions.assertNotNull(beanFactory.getBean("user2", User.class).getName());
	}

	@Test
	public void test2() {
		String xml = "classpath:framework/beandefinition/staticfactory-createbean.xml";
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		int beanCount = reader.loadBeanDefinitions(xml);
		Assertions.assertEquals(1, beanCount);
		Assertions.assertTrue(beanFactory.containsBeanDefinition("staticUserFactory"));
		Assertions.assertEquals(beanFactory.getBean("staticUserFactory", User.class),
				beanFactory.getBean("staticUserFactory", User.class));
	}

	@Test
	public void test3() {
		String xml = "classpath:framework/beandefinition/instancefactory-createbean.xml";
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		int beanCount = reader.loadBeanDefinitions(xml);
		Assertions.assertEquals(2, beanCount);
		Assertions.assertTrue(beanFactory.containsBeanDefinition("userFactory"));
		Assertions.assertTrue(beanFactory.containsBeanDefinition("user3"));
		Assertions.assertEquals(beanFactory.getBean("user3", User.class),
				beanFactory.getBean("user3", User.class));
	}
}
