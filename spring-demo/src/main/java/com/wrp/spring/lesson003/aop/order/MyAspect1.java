package com.wrp.spring.lesson003.aop.order;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 22:29
 **/
@Aspect
@Order(1)
@Component
public class MyAspect1 {

	@Pointcut("execution(* com.wrp.spring.lesson003.aop.order.Service2.*(..))")
	public void pc() {
	}

	@Before("pc()")
	public void before1() {
		System.out.println("MyAspect1 @Before1通知!");
	}

	@Around("pc()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("MyAspect1 @Around通知start");
		Object result = joinPoint.proceed();
		System.out.println("MyAspect1 @Around绕通知end");
		return result;
	}

	@After("pc()")
	public void after() throws Throwable {
		System.out.println("MyAspect1 @After通知!");
	}

	@AfterReturning("pc()")
	public void afterReturning() throws Throwable {
		System.out.println("MyAspect1 @AfterReturning通知!");
	}

	@AfterThrowing("pc()")
	public void afterThrowing() {
		System.out.println("MyAspect1 @AfterThrowing通知!");
	}

}
