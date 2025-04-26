package com.wrp.spring.framework.beandefinition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @author wrp
 * @since 2025-04-26 14:10
 **/
public class BeanOfXMLTest {

	@Test
	public void test() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/beandefinition.xml");
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			String[] aliases = beanFactory.getAliases(beanName);
			System.out.printf("beanName: %s, aliases: %s \n",beanName, Arrays.asList(aliases));
		}
	}

	@Test
	public void test2() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/innerbean.xml");
		System.out.println(beanFactory.getBeanDefinitionCount());
	}

	private static DefaultListableBeanFactory parseXml(String xmlPath) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(xmlPath);
		return beanFactory;
	}

	@Test
	public void test3() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/createbean.xml");
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			System.out.printf("beanName: %s，bean: %s\n", beanName, beanFactory.getBean(beanName));
		}
	}

	@Test
	public void test4() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/bean-scope.xml");
		String beanName = "user1";
		Assertions.assertEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));
	}

	@Test
	public void test5() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/bean-scope.xml");
		String beanName = "user2";
		Assertions.assertNotEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));
	}

	@Test
	public void test6() {
		DefaultListableBeanFactory beanFactory =
				parseXml("classpath:framework/beandefinition/bean-scope.xml");
		// 注册自定义scope
		beanFactory.registerScope(MyThreadScope.THREAD_SCOPE, new MyThreadScope());
		String beanName = "user3";
		Assertions.assertEquals(beanFactory.getBean(beanName), beanFactory.getBean(beanName));

		CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> beanFactory.getBean(beanName));
		CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> beanFactory.getBean(beanName));
		Simple bean1 = (Simple) future1.join();
		Simple bean2 = (Simple) future2.join();
		Assertions.assertNotEquals(bean1, bean2);
	}
}
