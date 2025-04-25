package com.wrp.spring.lesson003.aop;

/**
 * @author wrp
 * @since 2025-04-25 20:54
 **/
public class Service1 {
	public void m1() {
		System.out.println("我是 m1 方法");
	}

	public void m2() {
		System.out.println("我是 m2 方法");
	}

	public String say(String msg) {
		return  "你好：" + msg;
	}

	public String work(String msg) {
		return "开始工作了：" + msg;
	}
}
