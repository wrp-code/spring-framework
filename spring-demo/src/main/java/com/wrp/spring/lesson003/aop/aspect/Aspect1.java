package com.wrp.spring.lesson003.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * {@Before、@Around、@After、@AfterRunning、@AfterThrowing}
 * @author wrp
 * @since 2025-04-25 21:12
 **/
@Aspect
public class Aspect1 {

	//@2：定义了一个切入点，可以匹配Service1中所有方法
	@Pointcut("execution(* com.wrp.spring.lesson003.aop.Service1.*(..))")
	public void pointcut1() {
	}

	//@3：定义了一个前置通知，这个通知对刚刚上面我们定义的切入点中的所有方法有效
	@Before(value = "pointcut1()")
	public void before(JoinPoint joinPoint) {
		//输出连接点的信息
		System.out.println("前置通知，" + joinPoint);
	}

	//@4：定义了一个异常通知，这个通知对刚刚上面我们定义的切入点中的所有方法有效
	@AfterThrowing(value = "pointcut1()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		//发生异常之后输出异常信息
		System.out.println(joinPoint + ",发生异常：" + e.getMessage());
	}

}