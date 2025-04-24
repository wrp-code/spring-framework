package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月16日 10:49
 **/
public class ServiceC {
	private String name;
	private ServiceA serviceA;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceA getServiceA() {
		return serviceA;
	}

	public void setServiceA(ServiceA serviceA) {
		this.serviceA = serviceA;
	}

	@Override
	public String toString() {
		return "ServiceC{" +
				"name='" + name + '\'' +
				", serviceA=" + serviceA +
				'}';
	}
}