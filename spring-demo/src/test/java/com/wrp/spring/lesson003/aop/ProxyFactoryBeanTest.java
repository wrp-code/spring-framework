package com.wrp.spring.lesson003.aop;

import com.wrp.spring.lesson003.aop.proxyfactorybean.MainConfig1;
import com.wrp.spring.lesson003.aop.proxyfactorybean.MainConfig2;
import com.wrp.spring.lesson003.aop.proxyfactorybean.MainConfig3;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-25 20:59
 **/
public class ProxyFactoryBeanTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
//获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
		Service1 bean = context.getBean("service1Proxy", Service1.class);
		System.out.println("----------------------");
//调用代理的方法
		bean.m1();
		System.out.println("----------------------");
//调用代理的方法
		bean.m2();
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
		//获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
		Service1 bean = context.getBean("service1Proxy", Service1.class);
		System.out.println("----------------------");
		//调用代理的方法
		bean.m1();
		System.out.println("----------------------");
		//调用代理的方法
		bean.m2();
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
		//获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
		Service1 bean = context.getBean("service1Proxy", Service1.class);
		System.out.println("----------------------");
		//调用代理的方法
		bean.m1();
		System.out.println("----------------------");
		//调用代理的方法
		bean.m2();
	}
}
