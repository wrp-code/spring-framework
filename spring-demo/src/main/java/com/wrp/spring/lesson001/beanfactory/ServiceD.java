package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月16日 11:02
 **/
public class ServiceD {
	public void say() {
		ServiceA serviceA = this.getServiceA();
		System.out.println("this:" + this + ",serviceA:" + serviceA);
	}

	public ServiceA getServiceA() {
		// 不会执行
		System.out.println("ServiceD getServiceA()");
		return null;
	}
}
