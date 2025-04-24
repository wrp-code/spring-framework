package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 阶段五、加载beanClass为Class
 * @author wrp
 * @since 2025-04-21 22:15
 **/
public class ResolveBeanClassTest {

	@Test
	public void test() {
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Car.class.getName()).getBeanDefinition();
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerBeanDefinition("car", beanDefinition);

		Car car = beanFactory.getBean("car", Car.class);
		System.out.println(car);
	}
}
