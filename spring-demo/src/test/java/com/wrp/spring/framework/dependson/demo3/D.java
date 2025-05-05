package com.wrp.spring.framework.dependson.demo3;


import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
public class D implements DisposableBean {

	public D() {
		System.out.println("Create D...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy D...");
	}
}
