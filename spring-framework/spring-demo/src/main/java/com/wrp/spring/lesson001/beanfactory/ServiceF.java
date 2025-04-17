package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 需要实现 {@link ApplicationContextAware}
 * @author wrp
 * @since 2025年04月16日 11:10
 **/
public class ServiceF implements ApplicationContextAware { //@1

	public void say(){
		ServiceA serviceA = this.getServiceA();//@2
		System.out.println("this:"+this+",serviceA:"+ serviceA);
	}

	public ServiceA getServiceA() {
		return this.context.getBean(ServiceA.class);//@3
	}

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}