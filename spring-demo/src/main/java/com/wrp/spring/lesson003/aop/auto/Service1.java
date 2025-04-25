package com.wrp.spring.lesson003.aop.auto;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 22:11
 **/
@Component
public class Service1 {
	public void say(String name) {
		System.out.println("你好：" + name);;
	}
}