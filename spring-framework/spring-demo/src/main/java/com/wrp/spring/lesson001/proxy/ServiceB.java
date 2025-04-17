package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月16日 16:36
 **/
public class ServiceB implements IService {
	@Override
	public void m1() {
		System.out.println("我是ServiceB中的m1方法!");
	}

	@Override
	public void m2() {
		System.out.println("我是ServiceB中的m2方法!");
	}

	@Override
	public void m3() {
		System.out.println("我是ServiceB中的m3方法!");
	}
}