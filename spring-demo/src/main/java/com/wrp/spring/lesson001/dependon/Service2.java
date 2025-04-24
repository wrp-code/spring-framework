package com.wrp.spring.lesson001.dependon;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Component
public class Service2 {
	public Service2() {
		System.out.println("create Service2");
	}
}