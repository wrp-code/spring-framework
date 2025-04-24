package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.factory.DisposableBean;

import java.util.List;

/**
 * @author wrp
 * @since 2025年04月15日 14:25
 **/
public class SetterBean {

	public static class ServiceA implements DisposableBean {
		public ServiceA() {
			System.out.println("ServiceA creating");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println("ServiceA destroy");
		}
	}

	public static class ServiceB implements DisposableBean {

		ServiceA serviceA;

		public void setServiceA(ServiceA serviceA) {
			this.serviceA = serviceA;
		}

		public ServiceB() {
			System.out.println("ServiceB creating");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println("ServiceB destroy");
		}
	}

	private ServiceA service;

	public void setService(ServiceA service) {
		this.service = service;
	}

	List<ServiceA> services;

	public void setServices(List<ServiceA> services) {//@0
		System.out.println(services); //@1
		this.services = services;
	}
}
