package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 10:37
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann3 {
	String value();//@1
}

// 仅一个参数，且为value时，可省略
@Ann3("我是路人甲java") //@2
public class UseAnnotation3 {
}
