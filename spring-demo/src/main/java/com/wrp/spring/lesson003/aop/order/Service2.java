package com.wrp.spring.lesson003.aop.order;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 22:29
 **/
@Component
public class Service2 {
	public void say(String name) {
		System.out.println("你好：" + name);;
	}
}