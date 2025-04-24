package com.wrp.spring.lesson001.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wrp
 * @since 2025年04月16日 16:40
 **/
public class CostTimeInvocationHandler  implements InvocationHandler {

	private Object target;

	public CostTimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long starTime = System.nanoTime();
		Object result = method.invoke(this.target, args);//@1
		long endTime = System.nanoTime();
		System.out.println(this.target.getClass() + ".m1()方法耗时(纳秒):" + (endTime - starTime));
		return result;
	}

	/**
	 * 用来创建targetInterface接口的代理对象
	 *
	 * @param target          需要被代理的对象
	 * @param targetInterface 被代理的接口
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(T target, Class<T> targetInterface) {
		if (!targetInterface.isInterface()) {
			throw new IllegalStateException("targetInterface必须是接口类型!");
		} else if (!targetInterface.isAssignableFrom(target.getClass())) {
			throw new IllegalStateException("target必须是targetInterface接口的实现类!");
		}
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new CostTimeInvocationHandler(target));
	}
}