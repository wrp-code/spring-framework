package com.wrp.spring.lesson002.life.callback;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 12:08
 **/
@Component
public class Service2 {
	public Service2() {
		System.out.println("create " + this.getClass());
	}
}