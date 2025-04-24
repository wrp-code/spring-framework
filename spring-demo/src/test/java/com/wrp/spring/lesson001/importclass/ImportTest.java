package com.wrp.spring.lesson001.importclass;

import com.wrp.spring.lesson001.componentscan.importclass.*;
import com.wrp.spring.lesson001.componentscan.importclass.costtime.MainConfig6;
import com.wrp.spring.lesson001.componentscan.importclass.costtime.ServiceA;
import com.wrp.spring.lesson001.componentscan.importclass.costtime.ServiceB;
import com.wrp.spring.lesson001.componentscan.importclass.defer.MainConfig7;
import com.wrp.spring.lesson001.componentscan.importclass.defer.MainConfig8;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月21日 14:09
 **/
public class ImportTest {

	@Test
	public void test1() {
		// config类和两个普通类都注入成功了
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.printf("%s->%s%n", beanName, context.getBean(beanName));
		}
	}

	@Test
	public void test2() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test3() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test4() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test5() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig5.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test6() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig6.class);
		ServiceA service1 = context.getBean(ServiceA.class);
		ServiceB service2 = context.getBean(ServiceB.class);
		service1.m1();
		service2.m1();
	}

	@Test
	public void test7() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
	}

	@Test
	public void test8() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig8.class);
	}

	@Test
	public void test9() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig9.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test10() {
		//1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig10.class);
		//2.输出容器中定义的所有bean信息
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}
}
