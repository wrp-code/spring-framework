package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.demo2.DefaultMethodInfo;
import com.wrp.spring.framework.proxy.demo2.IMethodInfo;
import com.wrp.spring.framework.proxy.demo2.UserService;
import com.wrp.spring.framework.proxy.demo4.Service;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025-05-22 23:53
 **/
public class AopTest {

	@Test
	public void test1() {
		UserService userService = new UserService();
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(userService);
		Object proxy = proxyFactory.getProxy();

		System.out.println("代理对象的类型：" + proxy.getClass());
		System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
		System.out.println("代理对象实现的接口列表");
		for (Class<?> cf : proxy.getClass().getInterfaces()) {
			System.out.println(cf);
		}
	}

	@Test
	public void test2() {
		UserService userService = new UserService();
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(userService);
		proxyFactory.setInterfaces(IMethodInfo.class);
		proxyFactory.addAdvice(new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println(method);
			}
		});
		Object proxy = proxyFactory.getProxy();

		System.out.println("代理对象的类型：" + proxy.getClass());
		System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
		System.out.println("代理对象实现的接口列表");
		for (Class<?> cf : proxy.getClass().getInterfaces()) {
			System.out.println(cf);
		}

		IMethodInfo methodInfo = (IMethodInfo) proxy;
		// 调用方法会报错，因为接口的方法是abstract的，无法通过反射进行调用
		// 接口的默认方法也不可以
		methodInfo.hello();
	}

	@Test
	public void test3() {
		DefaultMethodInfo methodInfo = new DefaultMethodInfo(UserService.class);
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(methodInfo);
		proxyFactory.setInterfaces(IMethodInfo.class);
		Object proxy = proxyFactory.getProxy();

		System.out.println("代理对象的类型：" + proxy.getClass());
		System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
		System.out.println("代理对象实现的接口列表");
		for (Class<?> cf : proxy.getClass().getInterfaces()) {
			System.out.println(cf);
		}
	}

	@Test
	public void test4() {
		Service target = new Service();

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(target);

		proxyFactory.addAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				long startTime = System.nanoTime();
				Object result = invocation.proceed();
				long endTime = System.nanoTime();
				System.out.println(String.format("%s方法耗时(纳秒):%s", invocation.getMethod().getName(), endTime - startTime));
				return result;
			}
		});
		proxyFactory.setExposeProxy(true);
		Service proxy = (Service) proxyFactory.getProxy();
		proxy.m1();
	}
}
