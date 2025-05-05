package com.wrp.spring.framework.dependson.demo1;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
public class B implements DisposableBean {

	public B() {
		System.out.println("Create B...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy B...");
	}
}
