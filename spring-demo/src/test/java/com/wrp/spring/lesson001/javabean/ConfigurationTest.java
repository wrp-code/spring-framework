package com.wrp.spring.lesson001.javabean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author wrp
 * @since 2025年04月18日 12:19
 **/
public class ConfigurationTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ConfigBean.class);//@1
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean1.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean2.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}


	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean3.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ConfigBean2.class);
		context.refresh();
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean4.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			//别名
			String[] aliases = context.getAliases(beanName);
			System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
					beanName,
					Arrays.asList(aliases),
					context.getBean(beanName)));
		}
	}
}
