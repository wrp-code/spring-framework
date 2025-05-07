package com.wrp.spring.framework.autowirecandidate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-07 22:20
 **/
@Configuration
public class Config2 {

	@Bean(autowireCandidate = false)
	public A a1() {
		return new A();
	}
}
