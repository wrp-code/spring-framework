package com.wrp.spring.lesson002.messagesource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月23日 19:59
 **/
@Configuration
public class MainConfig3 {
	@Bean
	public MessageSource messageSource(){
		return new MessageSourceFromDb();
	}
}