package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月14日 16:23
 **/
public class SimpleTest {

	@Test
	public void loadByXml() {
		String beanXml = "classpath:/lesson001/bean.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(beanXml);
		HelloWorld helloWorld = applicationContext.getBean("helloWorld", HelloWorld.class);
		helloWorld.sayHello();
	}
}
