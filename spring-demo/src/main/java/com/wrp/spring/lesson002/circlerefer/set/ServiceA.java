package com.wrp.spring.lesson002.circlerefer.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 23:29
 **/
@Component
public class ServiceA {
	private ServiceB serviceB;

	@Autowired
	public void setServiceB(ServiceB serviceB) {
		this.serviceB = serviceB;
	}
}