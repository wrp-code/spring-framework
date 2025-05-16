package com.wrp.spring.framework.importclass.demo2;

import com.wrp.spring.framework.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月15日 9:35
 **/
@Configuration
public class MyConfig {

	@Bean
	public User user(String name) {
		return new User(name);
	}

	@Bean
	public String address() {
		return "chengdu";
	}
}
