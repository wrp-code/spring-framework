package com.wrp.spring.framework.proxy.demo3;

/**
 * @author wrp
 * @since 2025年05月20日 15:34
 **/
public class User {

	private String name;

	private User() {
		throw new RuntimeException("不要通过反射创建User对象");
	}

	public User(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				'}';
	}
}
