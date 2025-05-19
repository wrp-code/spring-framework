package com.wrp.spring.framework.importclass.demo8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-19 08:05
 **/
@Configuration
public class Config2 {

	@Bean
	public String name2() {
		System.out.println("name2");
		return"name2";
	}
}
