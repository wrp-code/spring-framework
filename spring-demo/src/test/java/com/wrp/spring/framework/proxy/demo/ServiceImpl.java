package com.wrp.spring.framework.proxy.demo;

/**
 * @author wrp
 * @since 2025-05-20 07:45
 **/
public class ServiceImpl implements IService {
	@Override
	public void m1() {
		System.out.println("ServiceImpl m1...");
	}

	@Override
	public void m2() {
		System.out.println("ServiceImpl m2...");
	}

	@Override
	public void m3() {
		System.out.println("ServiceImpl m3...");
	}
}
