package com.wrp.spring.lesson001.proxy;

import org.junit.jupiter.api.Test;

/**
 * @author wrp
 * @since 2025年04月16日 16:36
 **/
public class ProxyTest {
	@Test
	public void m1() {
		IService serviceA = new ServiceA();
		IService serviceB = new ServiceB();
		serviceA.m1();
		serviceA.m2();
		serviceA.m3();

		serviceB.m1();
		serviceB.m2();
		serviceB.m3();
	}

	@Test
	public void serviceProxy() {
		IService serviceA = new ServiceProxy(new ServiceA());//@1
		IService serviceB = new ServiceProxy(new ServiceB()); //@2
		serviceA.m1();
		serviceA.m2();
		serviceA.m3();

		serviceB.m1();
		serviceB.m2();
		serviceB.m3();
	}
}
