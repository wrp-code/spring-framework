package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.beanfactoryprocessor.LessonModel;
import com.wrp.spring.lesson002.beanfactoryprocessor.MainConfig0;
import com.wrp.spring.lesson002.beanfactoryprocessor.MainConfig3;
import com.wrp.spring.lesson002.beanfactoryprocessor.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-24 22:40
 **/
public class BeanDefinitionRegistryPostProcessorTest {

	@Test
	public void test0() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig0.class);
		context.refresh();
		System.out.println(context.getBean("userName"));
	}

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig0.class);
		context.refresh();
		context.getBeansOfType(String.class).forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig0.class);
		context.refresh();

		System.out.println(context.getBean(LessonModel.class));
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig3.class);
		context.refresh();

		context.getBeansOfType(UserModel.class).forEach((beanName, bean) -> {
			System.out.println(String.format("%s->%s", beanName, bean));
		});
	}
}
