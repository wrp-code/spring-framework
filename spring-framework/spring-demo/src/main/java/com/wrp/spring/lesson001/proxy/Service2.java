package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月17日 15:33
 **/
public class Service2 {
	public void m1() {
		System.out.println("我是m1方法");
		this.m2(); //@1 也会走代理方法
	}

	public void m2() {
		System.out.println("我是m2方法");
	}
}
