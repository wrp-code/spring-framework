package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 15:29
 **/
public class Condition2 implements Condition, Ordered {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
