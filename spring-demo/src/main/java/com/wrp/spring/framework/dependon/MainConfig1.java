package com.wrp.spring.framework.dependon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Configuration
public class MainConfig1 {

	@Bean
	public Service1 service1() {
		return new Service1();
	}

	@Bean
	public Service2 service2() {
		return new Service2();
	}

	@Bean
	public Service3 service3() {
		return new Service3();
	}

}