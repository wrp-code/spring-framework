package com.wrp.spring.framework.lookup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月08日 19:29
 **/
@Configuration
public class Config {

	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}
}
