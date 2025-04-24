package com.wrp.spring.lesson002.beanfactoryprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 22:39
 **/
@Component
public class MyBeanDefinitionRegistryPostProcessor1 implements BeanDefinitionRegistryPostProcessor, Ordered {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		//定义一个字符串类型的bean
		AbstractBeanDefinition userNameBdf = BeanDefinitionBuilder.
				genericBeanDefinition(String.class).
				addConstructorArgValue("wrp").
				getBeanDefinition();
		//将userNameBdf注册到spring容器中
		registry.registerBeanDefinition("my", userNameBdf);
	}

	@Override
	public int getOrder() {
		return 1;
	}
}