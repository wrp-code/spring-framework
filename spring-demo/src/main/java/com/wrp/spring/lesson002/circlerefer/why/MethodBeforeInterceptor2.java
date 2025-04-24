package com.wrp.spring.lesson002.circlerefer.why;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 解决早期Bean与最终Bean不一致的问题
 * @author wrp
 * @since 2025-04-24 08:23
 **/
@Component
public class MethodBeforeInterceptor2 implements SmartInstantiationAwareBeanPostProcessor {
	@Override
	public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		if ("service1".equals(beanName)) {
			//代理创建工厂，需传入被代理的目标对象
			ProxyFactory proxyFactory = new ProxyFactory(bean);
			//添加一个方法前置通知，会在方法执行之前调用通知中的before方法
			proxyFactory.addAdvice(new MethodBeforeAdvice() {
				@Override
				public void before(Method method, Object[] args, Object target) throws Throwable {
					System.out.println("你好,service1");
				}
			});
			//返回代理对象
			return proxyFactory.getProxy();
		}
		return bean;
	}
}