package com.wrp.spring.framework.configuration;

import com.wrp.spring.framework.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月04日 13:28
 */
@Configuration
public class ConfigBean {

	@Bean
	public User user() {
		return new User();
	}
}
