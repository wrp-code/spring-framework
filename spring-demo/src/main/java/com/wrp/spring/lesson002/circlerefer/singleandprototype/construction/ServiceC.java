package com.wrp.spring.lesson002.circlerefer.singleandprototype.construction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 07:47
 **/
@Component
public class ServiceC {
	ServiceD serviceD;

	@Autowired
	public ServiceC(ServiceD serviceD) {
		this.serviceD = serviceD;
	}
}
