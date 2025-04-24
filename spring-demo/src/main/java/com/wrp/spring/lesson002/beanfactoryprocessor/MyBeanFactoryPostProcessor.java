package com.wrp.spring.lesson002.beanfactoryprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 22:44
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("准备修改lessonModel bean定义信息!");
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("lessonModel");
		beanDefinition.getPropertyValues().add("name", "spring高手系列!");

		// BeanFactoryPostProcessor中禁止使用getBean，会提前初始化Bean
		beanFactory.getBean("user1");
	}

}