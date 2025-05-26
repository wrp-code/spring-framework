package com.wrp.spring.framework.proxy.aspectj.execution;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author wrp
 * @since 2025-05-26 07:56
 **/
@Aspect
public class Aspect1 {

	@Pointcut("execution(* com.wrp.spring.framework.proxy.aspectj.UserService.* (..))")
	public void pc() {}

	@Before("pc()")
	public void before() {
		System.out.println("前置通知");
	}
}
