package com.wrp.spring.framework.dependson.demo2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年05月05日 15:29
 */
@Component
public class C implements DisposableBean {

	public C() {
		System.out.println("Create C...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroy C...");
	}
}
