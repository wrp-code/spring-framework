package com.wrp.spring.lesson002.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wrp
 * @since 2025-04-23 22:21
 **/
public class UserRegisterEvent extends ApplicationEvent {
	//用户名
	private String userName;

	public UserRegisterEvent(Object source, String userName) {
		super(source);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
