package com.wrp.spring.lesson001.javabean;

import lombok.Data;

/**
 * @author wrp
 * @since 2025年05月04日 11:27
 */
@Data
public class River {

	public User chief;

	public void init() {
		System.out.println("初始化方法");
	}

	public void destroy() {
		System.out.println("销毁方法");
	}
}
