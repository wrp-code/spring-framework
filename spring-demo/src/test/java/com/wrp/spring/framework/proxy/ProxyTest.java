package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.demo.CostTimeInvocationHandler;
import com.wrp.spring.framework.proxy.demo.CostTimeProxy;
import com.wrp.spring.framework.proxy.demo.IService;
import com.wrp.spring.framework.proxy.demo.ServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wrp
 * @since 2025-05-20 07:45
 **/
public class ProxyTest {

	@Test
	public void test1() {
		ServiceImpl service = new ServiceImpl();
		CostTimeProxy proxy = new CostTimeProxy(service);
		proxy.m1();
		proxy.m2();
		proxy.m3();
	}

	@Test
	public void test2() {
		ServiceImpl service = new ServiceImpl();
		// 1. 创建代理类的处理器
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				long l = System.currentTimeMillis();
				Object res = method.invoke(service, args);
				System.out.println((System.currentTimeMillis() - l) + " 毫秒");
				return res;
			}
		};
		// 2. 创建代理实例
		IService proxyService = (IService) Proxy.newProxyInstance(
				IService.class.getClassLoader(),
				new Class[]{IService.class},
				invocationHandler);
		// 3. 调用代理的方法
		proxyService.m1();
		proxyService.m2();
		proxyService.m3();
	}

	@Test
	public void test3() {
		ServiceImpl service = new ServiceImpl();
		IService proxy = CostTimeInvocationHandler.createProxy(service);
		proxy.m1();
		proxy.m2();
		proxy.m3();
	}
}
