package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Bean;

/**
 * @author wrp
 * @since 2025年04月21日 14:48
 **/
public class MyConfig {

	@Bean
	public User bean() {
		return new User("wrp");
	}
}
