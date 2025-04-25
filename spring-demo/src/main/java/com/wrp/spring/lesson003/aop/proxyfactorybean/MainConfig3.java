package com.wrp.spring.lesson003.aop.proxyfactorybean;

import com.wrp.spring.lesson003.aop.Service1;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025-04-25 21:00
 **/
public class MainConfig3 {
	//注册目标对象
	@Bean
	public Service1 service1() {
		return new Service1();
	}

	//定义一个前置通知
	@Bean
	public MethodBeforeAdvice methodBeforeAdvice() {
		MethodBeforeAdvice methodBeforeAdvice = new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println("准备调用：" + method);
			}
		};
		return methodBeforeAdvice;
	}

	//定义一个环绕通知
	@Bean
	public MethodInterceptor methodInterceptor() {
		MethodInterceptor methodInterceptor = new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				long starTime = System.nanoTime();
				Object result = invocation.proceed();
				long endTime = System.nanoTime();
				System.out.println(invocation.getMethod() + ",耗时(纳秒)：" + (endTime - starTime));
				return result;
			}
		};
		return methodInterceptor;
	}

	//定义一个后置通知
	@Bean
	public AfterReturningAdvice afterReturningAdvice() {
		AfterReturningAdvice afterReturningAdvice = new AfterReturningAdvice() {
			@Override
			public void afterReturning( Object returnValue, Method method, Object[] args, Object target) throws Throwable {
				System.out.println(method + "，执行完毕!");
			}
		};
		return afterReturningAdvice;
	}

	//注册ProxyFactoryBean
	@Bean
	public ProxyFactoryBean service1Proxy() {
		//1.创建ProxyFactoryBean
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		//2.设置目标对象的bean名称
		proxyFactoryBean.setTargetName("service1");
		//3.设置拦截器的bean名称列表，此处批量注册
		proxyFactoryBean.setInterceptorNames("methodBeforeAdvice", "methodInterceptor", "afterReturningAdvice");
		return proxyFactoryBean;
	}
}
