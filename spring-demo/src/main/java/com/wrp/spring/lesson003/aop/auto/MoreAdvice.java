package com.wrp.spring.lesson003.aop.auto;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025-04-25 22:07
 **/
public class MoreAdvice {

	public static class Service3 {
		public void say(String name) {
			System.out.println("你好：" + name);;
		}
	}

	public static class MyMethodInterceptor implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println("我是MethodInterceptor start");
			//调用invocation.proceed()执行下一个拦截器
			Object result = invocation.proceed();
			System.out.println("我是MethodInterceptor end");
			//返回结果
			return result;
		}
	}

	public static class MyMethodBeforeAdvice implements MethodBeforeAdvice {

		@Override
		public void before(Method method, Object[] args, Object target) throws Throwable {
			System.out.println("我是MethodBeforeAdvice");
		}
	}

	public static class MyAfterReturningAdvice implements AfterReturningAdvice {

		@Override
		public void afterReturning( Object returnValue, Method method, Object[] args, Object target) throws Throwable {
			System.out.println("我是AfterReturningAdvice");
		}
	}

	public static class MyThrowsAdvice implements ThrowsAdvice {
		public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
			System.out.println("我是ThrowsAdvice");
		}
	}
}
