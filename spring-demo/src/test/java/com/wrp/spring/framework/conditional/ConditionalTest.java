package com.wrp.spring.framework.conditional;

import com.wrp.spring.framework.conditional.demo1.DemoConfig1;
import com.wrp.spring.framework.conditional.demo1.ImportConfig;
import com.wrp.spring.framework.conditional.demo2.Config;
import com.wrp.spring.framework.conditional.demo3.DemoConfig3;
import com.wrp.spring.framework.conditional.demo4.DemoConfig4;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月19日 10:50
 **/
public class ConditionalTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ImportConfig.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("bean：%s -》 %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(DemoConfig1.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("bean：%s -》 %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.printf("bean：%s -》 %s\n", beanDefinitionName,
					context.getBean(beanDefinitionName));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig4.class);
		context.getBeansOfType(String.class).forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig3.class);
	}
}
