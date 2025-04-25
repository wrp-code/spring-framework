package com.wrp.spring.lesson003.aop;

/**
 * @author wrp
 * @since 2025-04-25 08:23
 **/
public class Service implements IService {
	@Override
	public void say(String msg) {
		System.out.println("hello:" + msg);
	}
}
