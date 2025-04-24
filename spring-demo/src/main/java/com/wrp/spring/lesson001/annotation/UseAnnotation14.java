package com.wrp.spring.lesson001.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 12:04
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface A14 {
	String value() default "a";//@0
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@A14 //@6
@interface B14 { //@1

	String value() default "b";//@2

	@AliasFor(annotation = A14.class, value = "value") //@5
	String a14Value();
}

@B14(value = "路人甲Java",a14Value = "通过B14给A14的value参数赋值") //@3
public class UseAnnotation14 {
}
