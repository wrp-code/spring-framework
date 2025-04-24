package com.wrp.spring.lesson001.componentscan.importclass.defer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 14:45
 **/
@Configuration
public class Configuration1 {
	@Bean
	public String name1() {
		System.out.println("name1");
		return "name1";
	}
}
