package com.wrp.spring.lesson001.conditional.configCondition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:34
 **/
@Configuration
@Conditional(MyConfigurationCondition1.class)
//@Conditional(MyCondition1.class)
public class BeanConfig2 {
	@Bean
	public String name() {
		return "路人甲Java";
	}
}