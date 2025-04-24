package com.wrp.spring.lesson002.life;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wrp
 * @since 2025-04-21 21:29
 **/
public class Service2 {

	@Autowired
	private Service1 service1; //@1

	@Override
	public String toString() {
		return "Service2{" +
				"service1=" + service1 +
				'}';
	}
}
