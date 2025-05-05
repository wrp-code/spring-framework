package com.wrp.spring.framework;

import lombok.Data;

/**
 * @author wrp
 * @since 2025-04-26 09:08
 **/
@Data
public class User {
	String name;
	User innerUser;

	public User() {
		System.out.println("User无参构造器");
	}

	public User(String name) {
		System.out.println("User有参构造器");
		this.name = name;
	}
}
