package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * @author wrp
 * @since 2025年04月18日 10:40
 **/
@Target(value = {
		ElementType.TYPE_PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann7 {
	String value();
}


public class UseAnnotation7<@Ann7("T0是在类上声明的一个泛型类型变量") T0, @Ann7("T1是在类上声明的一个泛型类型变量") T1> {

	public <@Ann7("T2是在方法上声明的泛型类型变量") T2> void m1() {
	}


	public static void main(String[] args) throws NoSuchMethodException {
		for (TypeVariable<?> typeVariable : UseAnnotation7.class.getTypeParameters()) {
			print(typeVariable);
		}

		for (TypeVariable<?> typeVariable : UseAnnotation7.class.getDeclaredMethod("m1").getTypeParameters()) {
			print(typeVariable);
		}
	}

	private static void print(TypeVariable<?> typeVariable) {
		System.out.println("类型变量名称:" + typeVariable.getName());
		Arrays.stream(typeVariable.getAnnotations()).forEach(System.out::println);
	}
}