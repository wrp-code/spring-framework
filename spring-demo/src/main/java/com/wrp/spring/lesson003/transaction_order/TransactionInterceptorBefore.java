package com.wrp.spring.lesson003.transaction_order;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-29 07:12
 **/
@Component
@Aspect
@Order(1) //@1
public class TransactionInterceptorBefore {

	@Pointcut("execution(* com.wrp.spring.lesson003.transaction_order.UserService.*(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object tsBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("--------before start!!!");
		Object result = joinPoint.proceed();
		System.out.println("--------before end!!!");
		return result;
	}
}