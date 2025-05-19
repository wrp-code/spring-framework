package com.wrp.spring.lesson001.conditional;

import com.wrp.spring.lesson001.conditional.configCondition.MainConfig7;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月21日 15:17
 **/
public class ConditionTest {
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
		Map<String, IService> serviceMap = context.getBeansOfType(IService.class);
		serviceMap.forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig2.class);
		System.out.println(context.getBean("name"));
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
		Map<String, String> serviceMap = context.getBeansOfType(String.class);
		serviceMap.forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
		Map<String, String> serviceMap = context.getBeansOfType(String.class);
		serviceMap.forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig5.class);
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig6.class);
	}

	@Test
	public void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.printf("%s->%s\n", beanName, context.getBean(beanName));
		}
	}
}
