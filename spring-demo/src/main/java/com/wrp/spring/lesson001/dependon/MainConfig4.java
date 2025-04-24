package com.wrp.spring.lesson001.dependon;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Configurable
public class MainConfig4 {

	@Bean
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