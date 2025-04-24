package com.wrp.spring.lesson002.life.destruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月23日 12:17
 **/
@Configuration
public class Config {

	@Bean(destroyMethod = "customDestroyMethod") //@1
	public ServiceA serviceA() {
		return new ServiceA();
	}
}
