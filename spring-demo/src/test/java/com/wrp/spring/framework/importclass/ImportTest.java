package com.wrp.spring.framework.importclass;

import com.wrp.spring.framework.importclass.demo.DemoConfig1;
import com.wrp.spring.framework.importclass.demo2.DemoConfig2;
import com.wrp.spring.framework.importclass.demo3.DemoConfig3;
import com.wrp.spring.framework.importclass.demo4.DemoConfig4;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月15日 9:15
 **/
public class ImportTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig1.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig2.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig3.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig4.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}
}
