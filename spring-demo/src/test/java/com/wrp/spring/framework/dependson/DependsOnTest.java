package com.wrp.spring.framework.dependson;

import com.wrp.spring.framework.dependon.*;
import com.wrp.spring.framework.dependson.demo1.Config1;
import com.wrp.spring.framework.dependson.demo2.Config2;
import com.wrp.spring.framework.dependson.demo3.Config3;
import com.wrp.spring.framework.dependson.demo3.Config4;
import com.wrp.spring.framework.dependson.demo3.Config5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月05日 7:05
 */
public class DependsOnTest {

	// 创建顺序和声明顺序相同，销毁顺序与创建顺序相反
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig1.class);
		context.close();
	}

	// 创建顺序未知，销毁顺序与创建时相反
	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig2.class);
		context.close();
	}

	// 被依赖的先创建，销毁顺序与创建时相反
	@Test
	public void test3() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig3.class);
		context.close();
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig4.class);
		context.close();
	}

	// 创建顺序是声明时的顺序，销毁顺序与创建顺序相反
	@Test
	public void test5() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig5.class);
		context.close();
	}

	@Test
	public void test6() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:lesson001/dependson/no-dependson.xml");

		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			// 获取bean
			Object bean = beanFactory.getBean(beanDefinitionName);
			System.out.printf("beanName: %s, bean: %s\n", beanDefinitionName, bean);
		}

		// 销毁Bean
		beanFactory.destroySingletons();
	}

	@Test
	public void test7() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config1.class);
		context.close();
	}

	@Test
	public void test8() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config2.class);
		context.close();
	}

	@Test
	public void test9() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config3.class);
		context.close();
	}

	@Test
	public void test10() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config4.class);
		context.close();
	}

	@Test
	public void test11() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config5.class);
		context.close();
	}
}
