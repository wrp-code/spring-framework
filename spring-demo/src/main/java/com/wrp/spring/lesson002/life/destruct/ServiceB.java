package com.wrp.spring.lesson002.life.destruct;

import jakarta.annotation.PreDestroy;

/**
 * @author wrp
 * @since 2025年04月23日 12:13
 **/
public class ServiceB {
	public ServiceB() {
		System.out.println("create " + this.getClass());
	}

	@PreDestroy
	public void preDestroy() { //@1
		System.out.println("preDestroy()");
	}
}