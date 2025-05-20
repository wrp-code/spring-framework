package com.wrp.spring.framework.proxy.demo;

/**
 * @author wrp
 * @since 2025-05-20 07:46
 **/
public class CostTimeProxy implements IService {

	IService target;

	public CostTimeProxy(IService target) {
		this.target = target;
	}

	@Override
	public void m1() {
		long l = System.currentTimeMillis();
		target.m1();
		System.out.println((System.currentTimeMillis() - l) + "毫秒");
	}

	@Override
	public void m2() {
		long l = System.currentTimeMillis();
		target.m2();
		System.out.println((System.currentTimeMillis() - l) + "毫秒");
	}

	@Override
	public void m3() {
		long l = System.currentTimeMillis();
		target.m3();
		System.out.println((System.currentTimeMillis() - l) + "毫秒");
	}
}
