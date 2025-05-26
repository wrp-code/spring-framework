package com.wrp.spring.framework.proxy.demo5;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-05-25 18:59
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		registry.registerBeanDefinition("queryBean",
				BeanDefinitionBuilder.genericBeanDefinition(ProxyFactoryBean.class)
						.addPropertyValue("targetName", "queryUser")
						.addPropertyValue("interceptorNames", new String[]{"before"})
						.getBeanDefinition());

	}
}
