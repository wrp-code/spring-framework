package com.wrp.spring.framework.conditional.demo2;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025年05月19日 11:22
 **/
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnBeanCondition.class)
public @interface ConditionalOnMissingBean {
	Class<?>[] value() default {};

}
