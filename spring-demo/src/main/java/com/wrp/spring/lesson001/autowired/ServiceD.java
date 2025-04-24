package com.wrp.spring.lesson001.autowired;

import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;

/**
 * @author wrp
 * @since 2025年04月21日 16:38
 **/
@Component
public class ServiceD {

	@Resource
	private IService serviceA;//@1

	@Override
	public String toString() {
		return "Service2{" +
				"service1=" + serviceA +
				'}';
	}
}
