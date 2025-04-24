package com.wrp.spring.lesson001.componentscan.importclass;

/**
 * @author wrp
 * @since 2025年04月21日 14:08
 **/
public class Service2 {
	private Service1 service1;

	public Service1 getService1() {
		return service1;
	}

	public void setService1(Service1 service1) {
		this.service1 = service1;
	}

	@Override
	public String toString() {
		return "Service2{" +
				"service1=" + service1 +
				'}';
	}
}
