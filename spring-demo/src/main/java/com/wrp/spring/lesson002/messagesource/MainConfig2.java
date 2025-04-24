package com.wrp.spring.lesson002.messagesource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author wrp
 * @since 2025年04月23日 19:56
 **/
@Configuration
public class MainConfig2 {
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource result = new ReloadableResourceBundleMessageSource();
		result.setBasenames("lesson002/message");
		//设置缓存时间1000毫秒
		// -1：表示永远缓存
		//
		//0：每次获取国际化信息的时候，都会重新读取国际化文件
		//
		//大于0：上次读取配置文件的时间距离当前时间超过了这个时间，重新读取国际化文件
		result.setCacheMillis(1000);
		return result;
	}
}