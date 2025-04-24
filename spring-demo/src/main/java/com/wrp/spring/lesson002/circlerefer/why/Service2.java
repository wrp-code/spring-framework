package com.wrp.spring.lesson002.circlerefer.why;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 08:04
 **/
@Component
public class Service2 {

	public void m1() {
		System.out.println("Service2 m1");
		this.service1.m1();//@1
	}

	private Service1 service1;

	@Autowired
	public void setService1(Service1 service1) {
		this.service1 = service1;
	}

	public Service1 getService1() {
		return service1;
	}
}