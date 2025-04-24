package com.wrp.spring.lesson002.beanfactoryprocessor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wrp
 * @since 2025-04-24 22:48
 **/
public class UserModel {
	@Autowired
	private String name; //@1

	@Override
	public String toString() {
		return "UserModel{" +
				"name='" + name + '\'' +
				'}';
	}
}