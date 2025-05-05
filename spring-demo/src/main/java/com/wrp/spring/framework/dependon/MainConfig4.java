package com.wrp.spring.framework.dependon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Configuration
public class MainConfig4 {

	@Bean
	// dependson的顺序会影响创建顺序
//	@DependsOn({"service3", "service2"})//@1
	@DependsOn({"service2", "service3"})//@1
	public Service1 service1() {
		return new Service1();
	}

	@Bean
	public Service2 service2() {
		return new Service2();
	}

	@Bean
	public Service3 service3() {
		return new Service3();
	}

}