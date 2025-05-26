package com.wrp.spring.framework.proxy.aspectj;

/**
 * @author wrp
 * @since 2025-05-26 07:57
 **/
public class UserService implements Query {
	@Override
	public void query() {
		System.out.println("UserService query...");
	}

	@Override
	public void print(String name) {
		System.out.println(name + " print...");
	}
}
