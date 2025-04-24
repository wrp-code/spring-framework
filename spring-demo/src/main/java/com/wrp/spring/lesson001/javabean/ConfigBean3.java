package com.wrp.spring.lesson001.javabean;

import org.springframework.context.annotation.Bean;

/**
 * @author wrp
 * @since 2025年04月18日 12:24
 **/
public class ConfigBean3 {

	@Bean
	public ServiceA serviceA() {
		System.out.println("调用serviceA()方法"); //@0
		return new ServiceA();
	}

	@Bean
	ServiceB serviceB1() {
		System.out.println("调用serviceB1()方法");
		ServiceA serviceA = this.serviceA(); //@1
		return new ServiceB(serviceA);
	}

	@Bean
	ServiceB serviceB2() {
		System.out.println("调用serviceB2()方法");
		ServiceA serviceA = this.serviceA(); //@2
		return new ServiceB(serviceA);
	}

}