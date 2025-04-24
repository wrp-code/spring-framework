package com.wrp.spring.lesson002.life;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author wrp
 * @since 2025年04月23日 12:03
 **/
public class Service implements InitializingBean {
	// 自定义初始化方法
	public void init() {
		System.out.println("调用init()方法");
	}

	// 初始化
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("调用afterPropertiesSet()");
	}
}