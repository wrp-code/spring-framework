package com.wrp.spring.lesson001.javabean;

/**
 * @author wrp
 * @since 2025年04月18日 12:23
 **/
public class ServiceB {
	private ServiceA serviceA;

	public ServiceB(ServiceA serviceA) {
		this.serviceA = serviceA;
	}

	@Override
	public String toString() {
		return "ServiceB{" +
				"serviceA=" + serviceA +
				'}';
	}
}
