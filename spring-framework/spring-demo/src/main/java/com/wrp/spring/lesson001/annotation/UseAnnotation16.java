package com.wrp.spring.lesson001.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 12:08
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface A16 {
	String name() default "a";//@0
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@A16
@interface B16 { //@1

	// 自动将@AliasFor修饰的参数作为value和attribute的值
	@AliasFor(annotation = A16.class) //@5
	String name() default "b";//@2
}

@B16(name="我是v1") //@3
public class UseAnnotation16 {
}
