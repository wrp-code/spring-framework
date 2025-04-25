package com.wrp.spring.lesson003.aop.auto;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 21:57
 **/
@Component
public class CarService {
	public void say() {
		System.out.println("我是CarService");
	}
}