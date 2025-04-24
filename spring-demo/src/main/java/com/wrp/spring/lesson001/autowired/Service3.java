package com.wrp.spring.lesson001.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 16:29
 **/
@Component
public class Service3 {

	@Autowired
	private Service1 service1;//@1

	@Autowired
	private Service2 service2;//@2

	public void setService1(Service1 service1) {
		this.service1 = service1;
	}

	public void setService2(Service2 service2) {
		this.service2 = service2;
	}

	@Override
	public String toString() {
		return "Service3{" +
				"service1=" + service1 +
				", service2=" + service2 +
				'}';
	}
}