package com.wrp.spring.framework.componentscan;

import com.wrp.spring.framework.componentscan.demo1.Config;
import com.wrp.spring.framework.componentscan.demo2.Config2;
import com.wrp.spring.framework.componentscan.demo3.Config3;
import com.wrp.spring.framework.componentscan.demo4.Config4;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-05-16 07:33
 **/
public class ComponentScanTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("name: %s -> %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config2.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("name: %s -> %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config3.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("name: %s -> %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config4.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("name: %s -> %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}
}
