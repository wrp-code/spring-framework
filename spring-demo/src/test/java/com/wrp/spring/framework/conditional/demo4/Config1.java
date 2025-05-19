package com.wrp.spring.framework.conditional.demo4;

import com.wrp.spring.framework.conditional.demo2.IService;
import com.wrp.spring.framework.conditional.demo2.Service1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年05月19日 14:44
 **/
@Configuration
public class Config1 {

//	@Bean
	public IService service1() {
		return new Service1();
	}
}
