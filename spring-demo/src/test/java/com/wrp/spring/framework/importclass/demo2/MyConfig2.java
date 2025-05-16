package com.wrp.spring.framework.importclass.demo2;

import org.springframework.context.annotation.Bean;

/**
 * @author wrp
 * @since 2025年05月15日 9:36
 **/
public class MyConfig2 {

	@Bean
	public String name() {
		return "wrp";
	}
}
