package com.wrp.spring.framework.importclass.demo4;

import com.wrp.spring.framework.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年05月15日 11:29
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		registry.registerBeanDefinition("myBean", new RootBeanDefinition(User.class));

		registry.registerBeanDefinition("user", BeanDefinitionBuilder.genericBeanDefinition(User.class).getBeanDefinition());
	}
}
