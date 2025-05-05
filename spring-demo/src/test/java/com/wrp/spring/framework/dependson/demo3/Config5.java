package com.wrp.spring.framework.dependson.demo3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
@Configuration
public class Config5 {

	@DependsOn({"c","b","d"})
	@Bean
	public A a() {
		return new A();
	}

	@Bean
	public B b() {
		return new B();
	}

	@Bean
	public C c() {
		return new C();
	}

}
