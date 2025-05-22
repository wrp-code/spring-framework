package com.wrp.spring.framework.proxy.demo4;

import org.springframework.aop.framework.AopContext;

/**
 * @author wrp
 * @since 2025-05-22 23:54
 **/
public class Service {

	public void m1() {
		System.out.println("m1");
//		this.m2();
		((Service)AopContext.currentProxy()).m2();
	}

	public void m2() {
		System.out.println("m2");
	}
}
