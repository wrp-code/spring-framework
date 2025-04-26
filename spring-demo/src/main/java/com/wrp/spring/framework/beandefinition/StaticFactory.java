package com.wrp.spring.framework.beandefinition;

/**
 * @author wrp
 * @since 2025-04-26 15:56
 **/
public class StaticFactory {

	public static User createUser() {
		System.out.println("静态工厂方法创建User");
		return new User();
	}
}
