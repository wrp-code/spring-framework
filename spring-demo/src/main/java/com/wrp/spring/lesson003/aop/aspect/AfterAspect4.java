package com.wrp.spring.lesson003.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author wrp
 * @since 2025-04-25 21:48
 **/
@Aspect
public class AfterAspect4 {
	@Pointcut("execution(* com.wrp.spring.lesson003.aop.Service1.*(..))")
	public void pc() {
	}

	@After("pc()")
	public void after(JoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		System.out.println(String.format("%s,执行完毕!", methodSignature.getMethod()));
	}
}
