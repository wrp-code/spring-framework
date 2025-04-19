package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 10:37
 **/
@Ann2(name = "我是路人甲java") //@2
public class UseAnnotation2 {
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann2 { //@1
	String name();
}