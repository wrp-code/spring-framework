package com.wrp.spring.lesson001.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * @author wrp
 * @since 2025年04月18日 10:59
 **/
public class AnnotationTest {
	@Test
	public void m1() {
		for (Annotation annotation : UserAnnotation11.class.getAnnotations()) {
			System.out.println(annotation);
		}
	}

	@Test
	public void m2() {
		TypeVariable<Class<UserAnnotation11>>[] typeParameters = UserAnnotation11.class.getTypeParameters();
		for (TypeVariable<Class<UserAnnotation11>> typeParameter : typeParameters) {
			System.out.println(typeParameter.getName() + "变量类型注解信息：");
			Annotation[] annotations = typeParameter.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation);
			}
		}
	}

	@Test
	public void m3() throws NoSuchFieldException {
		Field nameField = UserAnnotation11.class.getDeclaredField("name");
		for (Annotation annotation : nameField.getAnnotations()) {
			System.out.println(annotation);
		}
	}

	@Test
	public void m4() throws NoSuchFieldException, ClassNotFoundException {
		Field field = UserAnnotation11.class.getDeclaredField("map");
		Type genericType = field.getGenericType();
		Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();

		AnnotatedType annotatedType = field.getAnnotatedType();
		AnnotatedType[] annotatedActualTypeArguments = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments();
		int i = 0;
		for (AnnotatedType actualTypeArgument : annotatedActualTypeArguments) {
			Type actualTypeArgument1 = actualTypeArguments[i++];
			System.out.println(actualTypeArgument1.getTypeName() + "类型上的注解如下：");
			for (Annotation annotation : actualTypeArgument.getAnnotations()) {
				System.out.println(annotation);
			}
		}
	}

	@Test
	public void m5() {
		Constructor<?> constructor = UserAnnotation11.class.getConstructors()[0];
		for (Annotation annotation : constructor.getAnnotations()) {
			System.out.println(annotation);
		}
	}

	@Test
	public void m6() throws NoSuchMethodException {
		Method method = UserAnnotation11.class.getMethod("m1", String.class);
		for (Annotation annotation : method.getAnnotations()) {
			System.out.println(annotation);
		}
	}

	@Test
	public void m7() throws NoSuchMethodException {
		Method method = UserAnnotation11.class.getMethod("m1", String.class);
		for (Parameter parameter : method.getParameters()) {
			System.out.println(String.format("参数%s上的注解如下:", parameter.getName()));
			for (Annotation annotation : parameter.getAnnotations()) {
				System.out.println(annotation);
			}
		}
	}


}
