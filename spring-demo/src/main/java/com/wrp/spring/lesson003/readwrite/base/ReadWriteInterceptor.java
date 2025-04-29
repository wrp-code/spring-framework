package com.wrp.spring.lesson003.readwrite.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wrp
 * @since 2025年04月29日 14:45
 **/
@Aspect
@Order(Integer.MAX_VALUE - 2)
@Component
public class ReadWriteInterceptor {

	@Pointcut("target(IService)")
	public void pointcut() {}


	@Around("pointcut()")
	public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
		boolean isFirst = false;

		try {
			if(DsTypeHolder.getDsType() == null) {
				isFirst = true;
			}
			if(isFirst) {
				Object lastArgs = getLastArgs(joinPoint);
				if(DsType.SLAVE.equals(lastArgs)) {
					DsTypeHolder.slave();
				} else {
					DsTypeHolder.master();
				}
			}
			return joinPoint.proceed();
		} finally {
			if(isFirst) {
				DsTypeHolder.clearDsType();
			}
		}
	}

	//获取当前目标方法的最后一个参数
	private Object getLastArgs(final ProceedingJoinPoint pjp) {
		Object[] args = pjp.getArgs();
		if (Objects.nonNull(args) && args.length > 0) {
			return args[args.length - 1];
		} else {
			return null;
		}
	}
}
