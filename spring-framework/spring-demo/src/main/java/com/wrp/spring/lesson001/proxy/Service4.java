package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月17日 16:12
 **/
public class Service4 {
	public void insert1() {
		System.out.println("我是insert1");
	}

	public void insert2() {
		System.out.println("我是insert2");
	}

	public String get1() {
		System.out.println("我是get1");
		return "get1";
	}

	public String get2() {
		System.out.println("我是get2");
		return "get2";
	}
}