package com.wrp.spring.lesson001.conditional;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月21日 15:19
 **/
public class OnMissingBeanCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		Map<String, IService> beansOfType = beanFactory.getBeansOfType(IService.class);
		return beansOfType.isEmpty();
	}
}
