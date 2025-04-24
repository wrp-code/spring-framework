package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月21日 15:22
 **/
@Conditional(EnvCondition.class) //@1
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvConditional {
	//环境(测试环境、开发环境、生产环境)
	enum Env { //@2
		TEST, DEV, PROD
	}

	//环境
	Env value() default Env.DEV; //@3
}