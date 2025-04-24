package com.wrp.spring.lesson001.autowired;

import com.wrp.spring.lesson001.autowired.qualifier.InjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author wrp
 * @since 2025-04-21 19:55
 **/
public class MainConfig13 {
	@Bean
	public IService serviceA() {
		return new ServiceA();
	}

	@Bean
	@Primary //@1
	public IService serviceB() {
		return new ServiceB();
	}

	@Bean
	public InjectService injectService() {
		return new InjectService();
	}
}
