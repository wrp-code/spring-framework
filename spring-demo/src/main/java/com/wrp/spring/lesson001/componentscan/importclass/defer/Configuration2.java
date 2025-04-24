package com.wrp.spring.lesson001.componentscan.importclass.defer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 14:45
 **/
@Configuration
public class Configuration2 {
	@Bean
	public String name2() {
		System.out.println("name2");
		return "name2";
	}
}