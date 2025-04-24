package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 阶段四、合并BeanDefinition
 * 将一些存在父类的BeanDefinition（parentName）合并信息
 * 合并后的BeanDefinition存储在AbstractBeanFactory中
 * @author wrp
 * @since 2025-04-21 21:49
 **/
public class MergedBeanDefinitionTest {

	@Test
	public void test1() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		int count = reader.loadBeanDefinitions("classpath:lesson002/beans-parent.xml");
		System.out.println("注册了：" + count + "个BeanDefinition");

		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			BeanDefinition mergedBeanDefinition = beanFactory.getMergedBeanDefinition(beanName);

			System.out.println(beanName);
			System.out.println("解析xml过程中注册的beanDefinition：" + beanDefinition);
			System.out.println("beanDefinition中的属性信息" + beanDefinition.getPropertyValues());
			System.out.println("合并之后得到的mergedBeanDefinition：" + mergedBeanDefinition);
			System.out.println("mergedBeanDefinition中的属性信息" + mergedBeanDefinition.getPropertyValues());
			System.out.println("---------------------------");
		}
	}
}
