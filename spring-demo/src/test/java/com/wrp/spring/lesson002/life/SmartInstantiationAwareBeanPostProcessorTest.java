package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 阶段6.2，实例化前，指定候选构造器
 * @author wrp
 * @since 2025-04-21 22:47
 **/
public class SmartInstantiationAwareBeanPostProcessorTest {

	@Test
	public void test1() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//创建一个SmartInstantiationAwareBeanPostProcessor,将其添加到容器中
		factory.addBeanPostProcessor(new MySmartInstantiationAwareBeanPostProcessor());

		factory.registerBeanDefinition("name",
				BeanDefinitionBuilder.
						genericBeanDefinition(String.class).
						addConstructorArgValue("路人甲Java").
						getBeanDefinition());

		factory.registerBeanDefinition("age",
				BeanDefinitionBuilder.
						genericBeanDefinition(Integer.class).
						addConstructorArgValue(30).
						getBeanDefinition());

		factory.registerBeanDefinition("person",
				BeanDefinitionBuilder.
						genericBeanDefinition(Person.class).
						getBeanDefinition());

		Person person = factory.getBean("person", Person.class);
		System.out.println(person);

	}
}
