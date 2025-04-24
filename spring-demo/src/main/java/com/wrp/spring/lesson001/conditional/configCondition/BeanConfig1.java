package com.wrp.spring.lesson001.conditional.configCondition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:33
 **/
@Configuration
public class BeanConfig1 {
	@Bean
	public Service service() {
		return new Service();
	}
}