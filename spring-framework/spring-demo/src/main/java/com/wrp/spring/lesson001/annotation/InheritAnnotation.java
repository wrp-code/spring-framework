package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.*;

/**
 * 类可以继承父类上被@Inherited修饰的注解，
 * 而不能继承接口上被@Inherited修饰的注解
 * @author wrp
 * @since 2025年04月18日 11:23
 **/
public class InheritAnnotation {

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@interface A1{ //@1
	}
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@interface A2{ //@2
	}

	@A1 //@3
	interface I1{}
	@A2 //@4
	static class C1{}

	static class C2 extends C1 implements I1{} //@5

	public static void main(String[] args) {
		for (Annotation annotation : C2.class.getAnnotations()) { //@6
			System.out.println(annotation);
		}
	}
}
