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

	@Transactional(propagation = Propagation.REQUIRED)
	public void required(String name) {
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", name);
	}
}