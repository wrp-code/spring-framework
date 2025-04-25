package com.wrp.spring.lesson003.aop;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author wrp
 * @since 2025-04-25 07:32
 **/
public class SendMsgThrowsAdvice implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object target, RuntimeException e) {
		//监控到异常后发送消息通知开发者
		System.out.println("异常警报：");
		System.out.println(String.format("method:[%s]，args:[%s]", method.toGenericString(), Arrays.stream(args).collect(Collectors.toList())));
		System.out.println(e.getMessage());
		System.out.println("请尽快修复bug！");
	}
}
