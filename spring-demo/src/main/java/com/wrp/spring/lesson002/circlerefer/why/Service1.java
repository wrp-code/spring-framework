package com.wrp.spring.lesson002.circlerefer.why;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 08:04
 **/
@Component
public class Service1 {
	public void m1() {
		System.out.println("Service1 m1");
	}

	private Service2 service2;

	@Autowired
	public void setService2(Service2 service2) {
		this.service2 = service2;
	}

}