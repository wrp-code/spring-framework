package com.wrp.spring.lesson003.aop.order;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 22:30
 **/
@Component
public class Advisor1 extends DefaultPointcutAdvisor {

	public Advisor1() {
		MethodInterceptor methodInterceptor = new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("Advisor1 start");
				Object result = invocation.proceed();
				System.out.println("Advisor1 end");
				return result;
			}
		};
		this.setAdvice(methodInterceptor);
	}

	@Override
	public int getOrder() {
		return 2;
	}
}