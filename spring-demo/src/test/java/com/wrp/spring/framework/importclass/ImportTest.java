package com.wrp.spring.framework.importclass;

import com.wrp.spring.framework.importclass.demo.DemoConfig1;
import com.wrp.spring.framework.importclass.demo2.DemoConfig2;
import com.wrp.spring.framework.importclass.demo3.DemoConfig3;
import com.wrp.spring.framework.importclass.demo4.DemoConfig4;
import com.wrp.spring.framework.importclass.demo5.DemoConfig5;
import com.wrp.spring.framework.importclass.demo6.DemoConfig6;
import com.wrp.spring.framework.importclass.demo6.Service1;
import com.wrp.spring.framework.importclass.demo6.Service2;
import com.wrp.spring.framework.importclass.demo7.DemoConfig7;
import com.wrp.spring.framework.importclass.demo8.DemoConfig8;
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

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig5.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test6() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig6.class);
		Service1 service1 = context.getBean(Service1.class);
		Service2 service2 = context.getBean(Service2.class);
		service1.m1();
		service2.m1();
	}

	@Test
	public void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig7.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test8() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig8.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s, %s\n" , beanDefinitionName, context.getBean(beanDefinitionName));
		}
	}
}
