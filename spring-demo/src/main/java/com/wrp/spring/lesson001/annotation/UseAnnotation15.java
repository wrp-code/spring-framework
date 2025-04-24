package com.wrp.spring.lesson001.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 12:06
 **/
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface A15 {
	@AliasFor("v2")//@1
	String v1() default "";

	@AliasFor("v1")//@2
	String v2() default "";
}

@A15(v1 = "我是v1") //@3
public class UseAnnotation15 {

	@A15(v2 = "我是v2") //@4
	private String name;
}
