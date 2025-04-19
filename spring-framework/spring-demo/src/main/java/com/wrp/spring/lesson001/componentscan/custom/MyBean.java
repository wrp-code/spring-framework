package com.wrp.spring.lesson001.componentscan.custom;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025-04-19 08:50
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MyBean {

	@AliasFor(annotation = Component.class)
	String value() default "";
}
