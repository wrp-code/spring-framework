package com.wrp.spring.framework.beandefinition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author wrp
 * @since 2025-04-26 09:09
 **/
public class BeanDefinitionTest {

	@Test
	public void genericBeanDefinition() {
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
				.getBeanDefinition();
		Assertions.assertEquals(GenericBeanDefinition.class, beanDefinition.getClass());
	}

	@Test
	public void rootBeanDefinition() {
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class)
				.getBeanDefinition();
		Assertions.assertEquals(RootBeanDefinition.class, beanDefinition.getClass());
	}

	@Test
	public void childBeanDefinition() {
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.childBeanDefinition("parent1")
				.getBeanDefinition();
		Assertions.assertEquals(ChildBeanDefinition.class, beanDefinition.getClass());
	}

	@Test
	public void annotationBeanDefinition() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:framework/beandefinition/beans.xml");
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			System.out.printf("beanName:%s，BeanDefinition:%s\n", beanName, beanFactory.getBeanDefinition(beanName));
		}

		System.out.println("============MergedBeanDefinition=============");
		System.out.println(beanFactory.getMergedBeanDefinition("user3"));
		System.out.println(beanFactory.getMergedBeanDefinition("user4"));
		System.out.println(beanFactory.getMergedBeanDefinition("user5"));
	}

	@Test
	public void getBean() {
		// bean生命周期阶段1.BeanDefinition定义阶段
		// bean生命周期阶段2.BeanDefinition元信息解析阶段
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class).getBeanDefinition();
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// bean生命周期阶段3. BeanDefinition注册阶段
		beanFactory.registerBeanDefinition("user", beanDefinition);
		User user = beanFactory.getBean("user", User.class);
		Assertions.assertNotNull(user);
	}
}
