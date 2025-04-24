package com.wrp.spring.lesson002.beanfactoryprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-04-24 22:48
 **/
@Configuration
@ComponentScan
public class MainConfig3 {
	@Bean
	public UserModel user1() {
		return new UserModel();
	}

	@Bean
	public UserModel user2() {
		return new UserModel();
	}

	@Bean
	public String name() {
		return "路人甲Java,带大家成功java高手!";
	}
}