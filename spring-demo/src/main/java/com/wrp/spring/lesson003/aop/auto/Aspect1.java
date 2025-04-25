package com.wrp.spring.lesson003.aop.auto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-25 21:58
 **/
@Component //@1
@Aspect //@2
public class Aspect1 {

	@Pointcut("execution(* com.wrp.spring.lesson003.aop.auto..*(..))") //@3
	public void pc() {
	}

	@Before("pc()") //@4
	public void before(JoinPoint joinPoint) {
		System.out.println("我是前置通知,target:" + joinPoint.getTarget()); //5
	}
}