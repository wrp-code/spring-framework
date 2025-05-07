package com.wrp.spring.framework.autowirecandidate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-05-07 22:10
 **/
public class CandidateTest {

	@Test
	public void test() {
		Assertions.assertThrows(UnsatisfiedDependencyException.class,
				()-> new AnnotationConfigApplicationContext(Config.class));
	}

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config1.class);
		Assertions.assertEquals(context.getBean(B.class).a, context.getBean("a2", A.class));
		Assertions.assertEquals(context.getBean(A.class), context.getBean("a2", A.class));
		Assertions.assertNotNull(context.getBean("a1", A.class));
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config2.class);
		Assertions.assertNotNull(context.getBean(A.class));
		Assertions.assertEquals(context.getBean(A.class).name, "a1");
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config3.class);
		Assertions.assertEquals(context.getBean(A.class).name, "a3");
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config4.class);
		Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
				() -> context.getBean(A.class));
	}

	@Test
	public void test5() {
		Assertions.assertThrows(UnsatisfiedDependencyException.class,
				() -> new AnnotationConfigApplicationContext(Config5.class));
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config6.class);
		Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
				() -> context.getBean(A.class));
	}
}
