package com.wrp.spring.lesson003.transaction;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025-04-28 22:50
 **/
@Service
public class Service3 {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 导致m2事务失效。需要获取代理对象进行调用
	public void m1() {
		System.out.println("m1...");
		Service3 proxy = (Service3) AopContext.currentProxy();
		proxy.m2();
	}

	@Transactional
	public void m2() {
		System.out.println("m2处理事务...");
	}


	public void m3() {
		Service3 proxy = (Service3) AopContext.currentProxy();
		proxy.m4();

	}

	@Transactional
	private void m4() {
		System.out.println("private方法加入事务");
	}

	public void m5() {
		Service3 proxy = (Service3) AopContext.currentProxy();
		// 6.2之后，protected方法也会走事务
		proxy.m6();
	}

	@Transactional
	protected void m6() {
		System.out.println("protected方法加入事务");
	}

}
