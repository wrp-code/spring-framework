package com.wrp.spring.framework.dependson.demo3;


import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
public class A implements DisposableBean {

	public A() {
		System.out.println("Create A...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy A...");
	}
}
