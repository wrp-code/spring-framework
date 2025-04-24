package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:16
 **/
@Conditional(MyCondition1.class) //@1
@Configuration
public class MainConfig3 {
	@Bean
	public String name() { //@1
		return "路人甲Java";
	}
}