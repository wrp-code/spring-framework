package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月16日 10:50
 **/
public class BeanExtendTest {

	ClassPathXmlApplicationContext context;

	@BeforeEach
	public void before() {
		String beanXml = "classpath:lesson001/beanExtend.xml";
		context = new ClassPathXmlApplicationContext(beanXml);
	}

	@Test
	public void extendBean() {
		System.out.println("serviceB:" + context.getBean(ServiceB.class));
		System.out.println("serviceC:" + context.getBean(ServiceC.class));
	}

	//org.springframework.beans.factory.BeanIsAbstractException:
	// Error creating bean with name 'baseService': Bean definition is abstract
	@Test
	public void getAbstract() {
		Assertions.assertThrowsExactly(BeanIsAbstractException.class,
				()->context.getBean("baseService"));
	}
}
