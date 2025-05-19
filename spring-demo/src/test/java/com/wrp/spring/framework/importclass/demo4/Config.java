package com.wrp.spring.framework.importclass.demo4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-19 07:49
 **/
@Configuration
public class Config {

	@Bean
	public String name() {
		return "wrp";
	}

	@Bean
	public String address() {
		return "成都";
	}
}
