package com.wrp.spring.lesson002.messagesource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author wrp
 * @since 2025年04月23日 16:16
 **/

@Configuration
public class MainConfig1 {
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource result = new ResourceBundleMessageSource();
		//可以指定国际化化配置文件的位置，格式：路径/文件名称,注意不包含【语言_国家.properties】含这部分
		result.setBasenames("lesson002/message"); //@1
		return result;
	}
}