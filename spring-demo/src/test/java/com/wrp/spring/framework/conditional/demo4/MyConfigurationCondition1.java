package com.wrp.spring.framework.conditional.demo4;

import com.wrp.spring.framework.conditional.demo2.IService;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断bean存不存在的问题，通常会使用ConfigurationCondition这个接口
 * @author wrp
 * @since 2025年05月19日 14:46
 **/
public class MyConfigurationCondition1 implements ConfigurationCondition {
	@Override
	public ConfigurationPhase getConfigurationPhase() {
		return ConfigurationPhase.REGISTER_BEAN;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return !context.getBeanFactory()
				.getBeansOfType(IService.class)
				.isEmpty();
	}
}
