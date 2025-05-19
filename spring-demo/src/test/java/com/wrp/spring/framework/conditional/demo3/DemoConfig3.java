package com.wrp.spring.framework.conditional.demo3;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年05月19日 14:37
 **/
@Order(1)
class Condition1 implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}
}

class Condition2 implements Condition, Ordered {
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

class Condition3 implements Condition, PriorityOrdered {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println(this.getClass().getName());
		return true;
	}

	@Override
	public int getOrder() {
		return 10;
	}
}

@Configuration
@Conditional({Condition1.class, Condition2.class, Condition3.class})
public class DemoConfig3 {
}
