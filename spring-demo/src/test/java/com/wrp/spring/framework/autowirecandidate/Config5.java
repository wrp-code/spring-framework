package com.wrp.spring.framework.autowirecandidate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-07 22:09
 **/
@Configuration
public class Config5 {

	@Bean(autowireCandidate = false)
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}
}
