package com.wrp.spring.lesson001.componentscan.importclass.costtime;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 14:31
 **/
@Component
public class ServiceB {
	public void m1() {
		System.out.println(this.getClass() + ".m1()");
	}
}
