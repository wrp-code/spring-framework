package com.wrp.spring.lesson001.beanfactory;

import java.util.List;

/**
 * @author wrp
 * @since 2025年04月15日 14:55
 **/
public class CandidateBean {
	public interface IService{ } //@1
	public static class ServiceA implements IService{} //@2
	public static class ServiceB implements IService{} //@3

	IService service;

	public void setService(IService service) {
		this.service = service;
	}

	List<IService> services;

	public void setService1(List<IService> services) {//@0
		this.services = services;
	}
}
