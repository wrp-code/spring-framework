package com.wrp.spring.framework.beandefinition;

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
	}

	public User(String name) {
		this.name = name;
	}
}
