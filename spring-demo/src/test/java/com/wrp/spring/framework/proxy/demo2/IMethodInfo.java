package com.wrp.spring.framework.proxy.demo2;

import java.util.List;

/**
 * @author wrp
 * @since 2025年05月20日 15:17
 **/
public interface IMethodInfo {
	//获取方法数量
	int methodCount();

	//获取被代理的对象中方法名称列表
	List<String> methodNames();

	default void hello() {
		System.out.println("hello, 接口默认方法");
	}
}
