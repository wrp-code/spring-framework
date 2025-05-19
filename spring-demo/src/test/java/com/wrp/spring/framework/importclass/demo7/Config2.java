package com.wrp.spring.framework.importclass.demo7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-05-19 07:58
 **/
@Configuration
public class Config2 {

	@Bean
	public String name2() {
		return "wrp2";
	}
}
