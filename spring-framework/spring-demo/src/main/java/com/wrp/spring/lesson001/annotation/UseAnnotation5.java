package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 10:38
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann5 {
	String[] name() default {"路人甲java", "spring系列"};//@1
	int[] score() default 1; //@2
	int age() default 30; //@3
	String address(); //@4
}

@Ann5(age = 32,address = "上海") //@5
public class UseAnnotation5 {

}