package com.wrp.spring.lesson001.proxy;

import java.io.Serializable;

/**
 * @author wrp
 * @since 2025年04月16日 16:36
 **/
public class ServiceA implements IService, Serializable {
	private static final long serialVersionUID = 1L; // 添加此行
	@Override
	public void m1() {
		System.out.println("我是ServiceA中的m1方法!");
	}

	@Override
	public void m2() {
		System.out.println("我是ServiceA中的m2方法!");
	}

	@Override
	public void m3() {
		System.out.println("我是ServiceA中的m3方法!");
	}
}