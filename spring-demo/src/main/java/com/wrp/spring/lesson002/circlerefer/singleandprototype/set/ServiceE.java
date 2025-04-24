package com.wrp.spring.lesson002.circlerefer.singleandprototype.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 07:47
 **/
@Component
public class ServiceE {
	ServiceF serviceF;

	@Autowired
	public void setServiceF(ServiceF serviceF) {
		this.serviceF = serviceF;
	}
}
