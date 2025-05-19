package com.wrp.spring.framework.conditional.demo2;

import org.springframework.context.annotation.Bean;

/**
 * @author wrp
 * @since 2025年05月19日 11:25
 **/
public class Config {

	@ConditionalOnMissingBean(IService.class)
	@Bean
	public IService service1() {
		return new Service1();
	}

	@ConditionalOnMissingBean(IService.class)
	@Bean
	public IService service2() {
		return new Service2();
	}
}
