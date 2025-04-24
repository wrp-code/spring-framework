package com.wrp.spring.lesson001.lazy;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author wrp
 * @since 2025-04-21 20:37
 **/
@Lazy //@1
@Configurable
public class MainConfig7 {

	@Bean
	public String name() {
		System.out.println("create bean:name");
		return "路人甲Java";
	}

	@Bean
	public String address() {
		System.out.println("create bean:address");
		return "上海市";
	}

	@Bean
	@Lazy(false) //@2
	public Integer age() {
		System.out.println("create bean:age");
		return 30;
	}
}