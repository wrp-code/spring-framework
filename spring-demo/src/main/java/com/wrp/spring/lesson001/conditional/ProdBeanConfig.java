package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:23
 **/
@Configuration
@EnvConditional(EnvConditional.Env.PROD) //@1
public class ProdBeanConfig {
	@Bean
	public String name() {
		return "我是生产环境!";
	}
}