package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * 需要实现{@link org.springframework.beans.factory.support.MethodReplacer}
 * 直接使用代理方法
 * @author wrp
 * @since 2025年04月16日 11:07
 **/
public class ReplacedMethodTest {
	@Test
	public void replacedmethod() {
		String beanXml = "classpath:lesson001/replacedmethod.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

		System.out.println(context.getBean(ServiceA.class)); //@1
		System.out.println(context.getBean(ServiceA.class)); //@2

		System.out.println("serviceD中的serviceA");
		ServiceD serviceD = context.getBean(ServiceD.class); //@3
		serviceD.say();
		serviceD.say();
	}
}
