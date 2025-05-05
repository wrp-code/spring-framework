package com.wrp.spring.lesson001.javabean;

import org.springframework.context.annotation.*;

/**
 * @author wrp
 * @since 2025年05月04日 11:29
 */
@Configuration
public class ConfigBean5 {

	@DependsOn("user")
	@Lazy
	@Primary
	@Scope("thread")
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public River river() {
		return new River();
	}

	@Bean
	public River river2() {
		return new River();
	}

	@Bean
	public User user() {
		return new User();
	}
}
