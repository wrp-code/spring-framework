package com.wrp.spring.lesson002.life.destruct;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年04月23日 12:12
 **/
public class ServiceA implements DisposableBean {

	public ServiceA() {
		System.out.println("创建ServiceA实例");
	}

	@PreDestroy
	public void preDestroy1() {
		System.out.println("preDestroy1()");
	}

	@PreDestroy
	public void preDestroy2() {
		System.out.println("preDestroy2()");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean接口中的destroy()");
	}

	//自定义的销毁方法
	public void customDestroyMethod() { //@1
		System.out.println("我是自定义的销毁方法:customDestroyMethod()");
	}
}