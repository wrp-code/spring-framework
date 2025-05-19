package com.wrp.spring.framework.importclass.demo7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-19 07:57
 **/
@Configuration
public class Config1 {

	@Bean
	public String name1() {
		return "wrp1";
	}

	@Bean
	public String name() {
		return "wrp before";
	}
}
