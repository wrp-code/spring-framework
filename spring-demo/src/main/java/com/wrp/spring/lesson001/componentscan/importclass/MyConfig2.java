package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 14:51
 **/
@Configuration
public class MyConfig2 {

	@Bean
	public User user() {
		return new User("222");
	}
}
