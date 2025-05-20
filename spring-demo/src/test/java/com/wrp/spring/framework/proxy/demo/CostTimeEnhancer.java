package com.wrp.spring.framework.proxy.demo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025年05月20日 10:26
 **/
public class CostTimeEnhancer {

	private static final CostTimeInterceptor COST_TIME_INTERCEPTOR = new CostTimeInterceptor();

	public static <T> T create(Class<T> target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target);
		enhancer.setCallback(COST_TIME_INTERCEPTOR);
		return (T) enhancer.create();
	}

	private static class CostTimeInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			long starTime = System.nanoTime();
			Object result = proxy.invokeSuper(obj, args);
			long endTime = System.nanoTime();
			System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
			return result;
		}
	}
}
