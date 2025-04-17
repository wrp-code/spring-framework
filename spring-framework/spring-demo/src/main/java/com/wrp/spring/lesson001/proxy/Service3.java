package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月17日 16:08
 **/
public class Service3 {
	public String m1() {
		System.out.println("我是m1方法");
		return "hello:m1";
	}

	public String m2() {
		System.out.println("我是m2方法");
		return "hello:m2";
	}

	public void m3() {
		System.out.println("我是m3方法");
	}
}