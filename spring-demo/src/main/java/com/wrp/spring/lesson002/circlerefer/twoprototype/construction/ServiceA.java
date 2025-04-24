package com.wrp.spring.lesson002.circlerefer.twoprototype.construction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 23:29
 **/
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceA {
	private ServiceB serviceB;

	@Autowired
	public ServiceA(ServiceB serviceB) {
		this.serviceB = serviceB;
	}
}