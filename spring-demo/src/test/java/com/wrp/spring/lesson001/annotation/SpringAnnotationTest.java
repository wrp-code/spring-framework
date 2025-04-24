package com.wrp.spring.lesson001.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author wrp
 * @since 2025年04月18日 12:05
 **/
public class SpringAnnotationTest {
	@Test
	public void test1() throws NoSuchFieldException {
		Annotation[] annotations = UseAnnotation12.class.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println(annotation);
		}
		System.out.println("-------------");
		Field v1 = UseAnnotation12.class.getDeclaredField("v1");
		Annotation[] declaredAnnotations = v1.getDeclaredAnnotations();
		for (Annotation declaredAnnotation : declaredAnnotations) {
			System.out.println(declaredAnnotation);
		}
	}

	@Test
	public void test2() {
		//AnnotatedElementUtils是spring提供的一个查找注解的工具类
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation13.class, B1.class));
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation13.class, A1.class));
	}

	@Test
	public void test3() {
		//AnnotatedElementUtils是spring提供的一个查找注解的工具类
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation14.class, B14.class));
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation14.class, A14.class));
	}

	@Test
	public void test4() throws NoSuchFieldException {
		//AnnotatedElementUtils是spring提供的一个查找注解的工具类
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation15.class, A15.class));
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation15.class.getDeclaredField("name"), A15.class));
	}

	@Test
	public void test5() throws NoSuchFieldException {
		//AnnotatedElementUtils是spring提供的一个查找注解的工具类
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation16.class, A16.class));
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation16.class, B16.class));
	}
}
