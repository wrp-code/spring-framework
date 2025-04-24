package com.wrp.spring.lesson001;

import com.wrp.spring.lesson001.autowired.MainConfig0;
import com.wrp.spring.lesson001.autowired.qualifier.MainConfig8;
import com.wrp.spring.lesson001.autowired.type.MainConfig18;
import com.wrp.spring.lesson001.autowired.type.OrderService;
import com.wrp.spring.lesson001.autowired.type.UserService;
import com.wrp.spring.lesson001.componentscan.importclass.MainConfig10;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月21日 16:22
 **/
public class InjectTest {
	@Test
	public void test0() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test8() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig8.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test10() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig8.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
		}
	}

	@Test
	public void test18() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig18.class);
		System.out.println(context.getBean(UserService.class).getDao());
		System.out.println(context.getBean(OrderService.class).getDao());
	}
}
