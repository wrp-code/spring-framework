package com.wrp.spring.framework.proxy.demo;

/**
 * @author wrp
 * @since 2025年05月20日 10:18
 **/
public class DefaultService {

	public String m1() {
		System.out.println("DefaultService m1...");
		return "m1";
	}

	public String m2() {
		System.out.println("DefaultService m2...");
		return "m2";
	}
}
