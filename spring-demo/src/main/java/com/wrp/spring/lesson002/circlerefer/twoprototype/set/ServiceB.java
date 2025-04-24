package com.wrp.spring.lesson002.circlerefer.twoprototype.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 23:30
 **/
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceB {
	private ServiceA serviceA;

	@Autowired
	public void setServiceA(ServiceA serviceA) {
		this.serviceA = serviceA;
	}
}
