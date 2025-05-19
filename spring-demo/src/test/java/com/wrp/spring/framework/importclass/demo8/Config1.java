package com.wrp.spring.framework.importclass.demo8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-19 08:05
 **/
@Configuration
public class Config1 {

	@Bean
	public String name1() {
		System.out.println("name1");
		return"name1";
	}
}
