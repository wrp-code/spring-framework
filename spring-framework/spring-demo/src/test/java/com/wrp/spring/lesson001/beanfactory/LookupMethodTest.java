package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 直接修改返回值
 * @author wrp
 * @since 2025年04月16日 11:03
 **/
public class LookupMethodTest {
	@Test
	public void lookupmethod() {
		String beanXml = "classpath:lesson001/lookupmethod.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

		System.out.println(context.getBean(ServiceA.class)); //@1
		System.out.println(context.getBean(ServiceA.class)); //@2

		System.out.println("serviceD中的serviceA");
		ServiceD serviceB = context.getBean(ServiceD.class); //@3
		serviceB.say();
		serviceB.say();
	}
}
