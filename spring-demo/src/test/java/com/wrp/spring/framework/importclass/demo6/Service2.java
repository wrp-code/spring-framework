package com.wrp.spring.framework.importclass.demo6;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-05-19 07:51
 **/
@Component
public class Service2 {
	public void m1() {
		System.out.println(this.getClass() + ".m1()");
	}
}