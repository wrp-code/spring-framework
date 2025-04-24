package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:21
 **/
@Configuration
public class BeanConfig1 {
	@Conditional(OnMissingBeanCondition.class) //@1
	@Bean
	public IService service1() {
		return new Service1();
	}
}