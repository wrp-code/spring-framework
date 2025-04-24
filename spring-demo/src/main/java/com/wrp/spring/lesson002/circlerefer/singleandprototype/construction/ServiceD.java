package com.wrp.spring.lesson002.circlerefer.singleandprototype.construction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 07:48
 **/
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceD {

	ServiceC serviceC;

	@Autowired
	public ServiceD(ServiceC serviceC) {
		this.serviceC = serviceC;
	}
}
