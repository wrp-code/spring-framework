package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:18
 **/
@Configuration
public class MainConfig4 {
	@Conditional(MyCondition1.class) //@1
	@Bean
	public String name() {
		return "路人甲Java";
	}

	@Bean
	public String address() {
		return "上海市";
	}
}