package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * @author wrp
 * @since 2025年04月16日 11:06
 **/
public class ServiceDMethodReplacer implements MethodReplacer, ApplicationContextAware {

	@Override
	public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
		return this.context.getBean(ServiceA.class);
	}

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}