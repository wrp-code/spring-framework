package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.demo.CostTimeInvocationHandler;
import com.wrp.spring.framework.proxy.demo.IService;
import com.wrp.spring.framework.proxy.demo.ServiceB;
import com.wrp.spring.framework.proxy.demo.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025年05月20日 14:12
 **/
public class DecompileTest {

	public static void main(String[] args) {

//		m1();
		m2();
	}

	private static void m1() {
		// JDK8
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		// 更高版本的JDK
		System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
		ServiceImpl service = new ServiceImpl();
		IService proxy = CostTimeInvocationHandler.createProxy(service);
		proxy.m1();
		proxy.m2();
		proxy.m3();
	}

	public static void m2() {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"E://temp");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setInterfaces(new Class[]{IService.class, ServiceB.class});
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println(method.getName());
				return null;
			}
		});
		Object proxy = enhancer.create();
		if(proxy instanceof ServiceB serviceB) {
			serviceB.doSomething();
		}
		if(proxy instanceof IService iService) {
			iService.m1();
		}

		System.out.println(proxy.getClass());
		System.out.println("parent: " + proxy.getClass().getSuperclass());
		for (Class<?> cs : proxy.getClass().getInterfaces()) {
			System.out.println(cs);
		}
	}
}
