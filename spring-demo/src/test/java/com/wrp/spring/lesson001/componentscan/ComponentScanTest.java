package com.wrp.spring.lesson001.componentscan;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-19 08:38
 **/
public class ComponentScanTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ScanBean1.class);
		context.refresh();
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean2.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean3.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean4.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean5.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean6.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}
}
