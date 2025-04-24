package com.wrp.spring.lesson001.conditional.configCondition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 15:37
 **/
public class MyConfigurationCondition1 implements ConfigurationCondition {
	@Override
	public ConfigurationPhase getConfigurationPhase() {
		return ConfigurationPhase.REGISTER_BEAN; //@1 条件在Bean注册阶段生效
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		//获取spring容器
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//判断容器中是否存在Service类型的bean
		boolean existsService = !beanFactory.getBeansOfType(Service.class).isEmpty();
		return existsService;
	}
}