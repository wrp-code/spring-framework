package com.wrp.spring.framework.proxy.demo;

import org.assertj.core.util.Arrays;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wrp
 * @since 2025-05-20 07:59
 **/
public class CostTimeInvocationHandler implements InvocationHandler {
	private Object target;

	public CostTimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
		long l = System.currentTimeMillis();
		Object res = method.invoke(target, objects);
		System.out.println((System.currentTimeMillis() - l) + " 毫秒");
		return res;
	}

	public static <T> T createProxy(T target) {

		if(Arrays.isNullOrEmpty(target.getClass().getInterfaces())) {
			throw new IllegalArgumentException("jdk代理只能代理接口类");
		}
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new CostTimeInvocationHandler(target));
	}
}
