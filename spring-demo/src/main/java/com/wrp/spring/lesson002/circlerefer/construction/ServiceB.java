package com.wrp.spring.lesson002.circlerefer.construction;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 23:30
 **/
@Component
public class ServiceB {
	private ServiceA serviceA;

	public ServiceB(ServiceA serviceA) {
		this.serviceA = serviceA;
	}
}