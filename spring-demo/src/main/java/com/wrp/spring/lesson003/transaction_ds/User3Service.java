package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 14:09
 **/
@Component
public class User3Service {
	@Autowired
	private JdbcTemplate jdbcTemplate3;

	@Autowired
	private User1Service user1Service;

	@Transactional(transactionManager = "transactionManager3", propagation = Propagation.REQUIRED)
	public void required() {
		this.jdbcTemplate3.update("insert into public.user2(name) VALUES (?)", "张三");
		this.user1Service.required();
		throw new RuntimeException();
	}

}