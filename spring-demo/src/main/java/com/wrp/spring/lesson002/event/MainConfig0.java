package com.wrp.spring.lesson002.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wrp
 * @since 2025-04-23 21:58
 **/
@Configuration
@ComponentScan
public class MainConfig0 {

	/**
	 * 注册一个bean：事件发布者
	 *
	 */
	@Bean
	public EventMulticaster eventMulticaster(@Autowired(required = false)List<EventListener> eventListeners) { //@1
		EventMulticaster eventPublisher = new SimpleEventMulticaster();
		if (eventListeners != null) {
			eventListeners.forEach(eventPublisher::addEventListener);
		}
		return eventPublisher;
	}

	/**
	 * 注册一个bean：用户注册服务
	 *
	 */
	@Bean
	public UserRegisterService userRegisterService(EventMulticaster eventMulticaster) { //@2
		UserRegisterService userRegisterService = new UserRegisterService();
		userRegisterService.setEventMulticaster(eventMulticaster);
		return userRegisterService;
	}
}