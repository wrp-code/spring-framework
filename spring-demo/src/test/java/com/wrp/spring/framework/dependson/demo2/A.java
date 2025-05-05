package com.wrp.spring.framework.dependson.demo2;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
@Component
public class A implements DisposableBean {

	public A() {
		System.out.println("Create A...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy A...");
	}
}
