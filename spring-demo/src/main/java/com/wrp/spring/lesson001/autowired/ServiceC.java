package com.wrp.spring.lesson001.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月21日 16:31
 **/
@Component
public class ServiceC implements IService{
	// 多个候选者时，根据字段名称注入
	@Autowired
	private IService serviceA; //@1

	// 没有ServiceC本身
	@Autowired
	private List<IService> services;

	@Autowired
	Map<String, IService> serviceMap;

	@Override
	public String toString() {
		return "Service2{\n" +
				"serviceA=" + serviceA +
				"services=" + services +
				", \n serviceMap=" + serviceMap +
				'}';
	}
}
