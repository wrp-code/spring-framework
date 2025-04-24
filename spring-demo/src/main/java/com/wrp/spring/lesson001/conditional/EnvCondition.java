package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 15:22
 **/
public class EnvCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		//当前需要使用的环境
		EnvConditional.Env curEnv = EnvConditional.Env.DEV; //@1
		//获取使用条件的类上的EnvCondition注解中对应的环境
		EnvConditional.Env env = (EnvConditional.Env) metadata
				.getAllAnnotationAttributes(EnvConditional.class.getName()).get("value").get(0);
		return env.equals(curEnv);
	}

}