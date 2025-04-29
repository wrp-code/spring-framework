package com.wrp.spring.lesson003.transaction_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025-04-29 07:10
 **/
@Component
public class UserService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public void addUser() {
		System.out.println("--------UserService.addUser start");
		this.jdbcTemplate.update("insert into public.user1(name) VALUES (?)", "张三");
		System.out.println("--------UserService.addUser end");
	}

}
