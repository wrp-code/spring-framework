package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月16日 10:26
 **/
public class LazyBeanTest {
	@Test
	public void actualTimeBean() {
		System.out.println("spring容器启动中...");
		String beanXml = "classpath:lesson001/actualTimeBean.xml";
		new ClassPathXmlApplicationContext(beanXml); //启动spring容器
		System.out.println("spring容器启动完毕...");
	}

	@Test
	public void lazyInitBean() {
		System.out.println("spring容器启动中...");
		String beanXml = "classpath:lesson001/lazyInitBean.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml); //启动spring容器
		System.out.println("spring容器启动完毕...");
		System.out.println("从容器中开始查找LazyInitBean");
		LazyInitBean lazyInitBean = context.getBean(LazyInitBean.class);
		System.out.println("LazyInitBean:" + lazyInitBean);
	}

	@Test
	public void actualTimeDependencyLazyBean() {
		System.out.println("spring容器启动中...");
		String beanXml = "classpath:lesson001/actualTimeDependencyLazyBean.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml); //启动spring容器
		System.out.println("spring容器启动完毕...");
	}
}
