package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月15日 10:56
 **/
public class MyScopeModel {
	public MyScopeModel(String beanScope) {
		System.out.println(String.format("线程:%s,create BeanScopeModel,{sope=%s},{this=%s}", Thread.currentThread(), beanScope, this));
	}
}
