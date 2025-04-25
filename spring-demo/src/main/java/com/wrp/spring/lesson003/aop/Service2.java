package com.wrp.spring.lesson003.aop;

import org.springframework.aop.framework.AopContext;

/**
 * @author wrp
 * @since 2025-04-25 08:25
 **/
public class Service2 {
	public void m1() {
		System.out.println("m1");
//		this.m2();
		((Service2) AopContext.currentProxy()).m2();
	}

	public void m2() {
		System.out.println("m2");
	}
}
