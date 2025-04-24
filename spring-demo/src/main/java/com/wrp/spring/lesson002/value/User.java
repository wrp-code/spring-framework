package com.wrp.spring.lesson002.value;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wrp
 * @since 2025年04月23日 15:43
 **/
@Component
@MyScope //@1
public class User {

	private String username;

	public User() {
		System.out.println("---------创建User对象" + this); //@2
		this.username = UUID.randomUUID().toString(); //@3
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}