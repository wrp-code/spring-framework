package com.wrp.spring.framework.beandefinition;

/**
 * @author wrp
 * @since 2025-04-26 16:00
 **/
public class UserFactory {

	public User createUser() {
		System.out.println("实例工厂方法模式创建User");
		return new User();
	}

	public User otherCreate() {
		return null;
	}
}
