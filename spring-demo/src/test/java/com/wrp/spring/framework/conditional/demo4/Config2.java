package com.wrp.spring.framework.conditional.demo4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月19日 14:44
 **/
@Conditional({MyConfigurationCondition1.class})
@Configuration
public class Config2 {

	@Bean
	public String name() {
		return "wrp";
	}
}
