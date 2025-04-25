package com.wrp.spring.lesson003.aop.auto;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 22:11
 **/
@Component
@Aspect
public class Aspect4 {
	@Pointcut("execution(* com.wrp.spring.lesson003.aop.auto.Service1.*(..))")
	public void pc() {
	}

	@Before("pc()")
	public void before() {
		System.out.println("@Before通知!");
	}

	@Around("pc()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("@Around通知start");
		Object result = joinPoint.proceed();
		System.out.println("@Around绕通知end");
		return result;
	}

	@After("pc()")
	public void after() throws Throwable {
		System.out.println("@After通知!");
	}

	@AfterReturning("pc()")
	public void afterReturning() throws Throwable {
		System.out.println("@AfterReturning通知!");
	}



	@AfterThrowing("pc()")
	public void afterThrowing() {
		System.out.println("@AfterThrowing通知!");
	}

}