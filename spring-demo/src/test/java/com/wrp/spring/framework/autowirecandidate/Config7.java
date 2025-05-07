package com.wrp.spring.framework.autowirecandidate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-07 22:09
 **/
@Configuration
public class Config7 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}

	@Bean(autowireCandidate = false)
	public A a2() {
		return new A();
	}

	@Bean
	public A a3() {
		return new A();
	}

	@Bean
	public C c() {
		return new C();
	}
}
