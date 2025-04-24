package com.wrp.spring.lesson001.autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-04-21 19:57
 **/
@Configuration
public class MainConfig15 {
	@Bean
	public Service1 service1() {
		return new Service1();
	}

	@Bean
	public Service2 service2() {
		return new Service2();
	}

	// 会将参数中的两个参数注入进来
	@Bean
	public Service3 service3(Service1 s1, Service2 s2) { //@0
		Service3 service3 = new Service3();
		service3.setService1(s1); //@1
		service3.setService2(s2); //@2
		return service3;
	}
}