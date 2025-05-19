package com.wrp.spring.framework.conditional.demo1;

import com.wrp.spring.framework.conditional.MyCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月19日 10:48
 **/

@Configuration
public class DemoConfig2 {

	@Conditional(MyCondition.class)
	@Bean
	public String name2() {
		return "wrp2";
	}

	@Bean
	public String address2() {
		return "成都2";
	}
}
