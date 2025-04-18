package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月16日 16:37
 **/
public class ServiceProxy implements IService {
	//目标对象，被代理的对象
	private IService target;

	public ServiceProxy(IService target) {
		this.target = target;
	}

	@Override
	public void m1() {
		long starTime = System.nanoTime();
		this.target.m1();
		long endTime = System.nanoTime();
		System.out.println(this.target.getClass() + ".m1()方法耗时(纳秒):" + (endTime - starTime));
	}

	@Override
	public void m2() {
		long starTime = System.nanoTime();
		this.target.m1();
		long endTime = System.nanoTime();
		System.out.println(this.target.getClass() + ".m1()方法耗时(纳秒):" + (endTime - starTime));
	}

	@Override
	public void m3() {
		long starTime = System.nanoTime();
		this.target.m1();
		long endTime = System.nanoTime();
		System.out.println(this.target.getClass() + ".m1()方法耗时(纳秒):" + (endTime - starTime));
	}
}