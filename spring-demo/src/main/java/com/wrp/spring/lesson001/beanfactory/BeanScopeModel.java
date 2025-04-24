package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月15日 10:42
 **/
public class BeanScopeModel {
	public BeanScopeModel(String beanScope) {
		System.out.println(String.format("create BeanScopeModel,{sope=%s},{this=%s}", beanScope, this));
	}
}
