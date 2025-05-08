package com.wrp.spring.framework.lookup;

import org.springframework.beans.factory.annotation.Lookup;

/**
 * @author wrp
 * @since 2025年05月08日 19:27
 **/
public class B {

	public void print() {
		A a = this.getA();
		System.out.println(a);
	}

	@Lookup("a")
	public A getA() {
		System.out.println("方法不会被执行");
		return null;
	}
}
