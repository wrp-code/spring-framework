package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 14:11
 **/
@Configuration
public class ConfigModule1 {

	@Bean
	public String module1() {
		return "module1";
	}
}
