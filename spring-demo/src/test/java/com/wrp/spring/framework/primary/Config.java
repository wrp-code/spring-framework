package com.wrp.spring.framework.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wrp
 * @since 2025年05月05日 18:02
 */
@ComponentScan
@Configuration
public class Config {

	@Primary
	@Bean
	public A a1() {
		return new A();
	}

	@Bean
	public A a2() {
		return new A();
	}

}
