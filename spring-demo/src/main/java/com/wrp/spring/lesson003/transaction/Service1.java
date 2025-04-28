package com.wrp.spring.lesson003.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025-04-27 22:47
 **/
@Component
public class Service1 {
	@Autowired
	private Service2 service2;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void m1() {
		this.jdbcTemplate.update("INSERT into t1 values ('m1')");
		this.service2.m2();
	}

	// 加入或创建新事务
	@Transactional(propagation = Propagation.REQUIRED)
	public void required(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	// 创建新事务
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void requires_new(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	// 创建事务或子事务
	@Transactional(propagation = Propagation.NESTED)
	public void nested(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	// 有则加入，无则非事务方式执行
	@Transactional(propagation = Propagation.SUPPORTS)
	public void supports(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	//有则加入，无则报错
	@Transactional(propagation = Propagation.MANDATORY)
	public void mandatory(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	// 非事务方式执行，有则挂起
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void not_supported(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}

	// 非事务方式执行，有则报错
	@Transactional(propagation = Propagation.NEVER)
	public void never(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}
}