package com.wrp.spring.framework.conditional.demo2;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年05月19日 11:22
 **/
public class OnBeanCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Class[] o = (Class[]) metadata.getAnnotationAttributes(ConditionalOnMissingBean.class.getName()).get("value");
		for (Class c : o) {
			if(!context.getBeanFactory().getBeansOfType(c).isEmpty()) {
				return false;
			}
		}

		return true;
	}
}
