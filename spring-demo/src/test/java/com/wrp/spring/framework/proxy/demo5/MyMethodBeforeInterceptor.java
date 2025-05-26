package com.wrp.spring.framework.proxy.demo5;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-05-25 19:03
 **/
@Component("before")
public class MyMethodBeforeInterceptor implements MethodInterceptor {
	@Nullable
	@Override
	public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
		System.out.println("前置通知：" + invocation.getMethod());
		return invocation.proceed();
	}
}
