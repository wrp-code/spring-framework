package com.wrp.spring.lesson001.componentscan.importclass.costtime;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025年04月21日 14:31
 **/
public class CostTimeProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		long starTime = System.nanoTime();
		//调用被代理对象（即target）的方法，获取结果
//		Object result = method.invoke(target, objects); //@1
		Object result = methodProxy.invokeSuper(o, objects); //@1
		long endTime = System.nanoTime();
		System.out.println(method + "，耗时(纳秒)：" + (endTime - starTime));
		return result;
	}

	/**
	 * 创建任意类的代理对象
	 *
	 */
	public static <T> T createProxy(T target) {
		CostTimeProxy costTimeProxy = new CostTimeProxy();
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(costTimeProxy);
		enhancer.setSuperclass(target.getClass());
		return (T) enhancer.create();
	}
}
