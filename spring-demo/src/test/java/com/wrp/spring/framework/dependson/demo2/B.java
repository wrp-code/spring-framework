package com.wrp.spring.framework.dependson.demo2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
@Component
public class B implements DisposableBean {

	public B() {
		System.out.println("Create B...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy B...");
	}
}
