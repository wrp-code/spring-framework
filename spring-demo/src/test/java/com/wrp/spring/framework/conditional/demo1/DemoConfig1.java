package com.wrp.spring.framework.conditional.demo1;

import com.wrp.spring.framework.conditional.MyCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月19日 10:48
 **/
@Conditional(MyCondition.class)
@Configuration
public class DemoConfig1 {

	@Bean
	public String name1() {
		return "wrp1";
	}

	@Bean
	public String address1() {
		return "成都1";
	}
}
