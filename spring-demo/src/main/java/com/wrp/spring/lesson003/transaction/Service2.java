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
public class Service2 {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void m2() {
		this.jdbcTemplate.update("INSERT into t1 values ('m2')");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void required(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void required_exception(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
		throw new RuntimeException();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void requires_new(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void requires_new_exception(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
		throw new RuntimeException();
	}

	@Transactional(propagation = Propagation.NESTED)
	public void nested(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
	}

	@Transactional(propagation = Propagation.NESTED)
	public void nested_exception(String name) {
		this.jdbcTemplate.update("insert into public.user2(name) VALUES (?)", name);
		throw new RuntimeException();
	}
}

