package com.wrp.spring.lesson001.proxy;

/**
 * @author wrp
 * @since 2025年04月16日 16:35
 **/
public interface IService {
	void m1();
	void m2();
	void m3();

	// 不代理
	private void m4() {
	}

	default void m5() {}

	// 接口中不允许有final方法

	// 不代理
	static void m6() {}
}
