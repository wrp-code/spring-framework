package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 14:24
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//定义一个bean：Service1
		BeanDefinition service1BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service1.class).getBeanDefinition();
		//注册bean
		registry.registerBeanDefinition("service1", service1BeanDinition);

		//定义一个bean：Service2，通过addPropertyReference注入service1
		BeanDefinition service2BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service2.class).
				addPropertyReference("service1", "service1").
				getBeanDefinition();
		//注册bean
		registry.registerBeanDefinition("service2", service2BeanDinition);
	}
}
