package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:23
 **/
@Configuration
@EnvConditional(EnvConditional.Env.DEV) //@1
public class DevBeanConfig {
	@Bean
	public String name() {
		return "我是开发环境!";
	}
}