package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 15:29
 **/
public class Condition3 implements Condition, PriorityOrdered {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}
