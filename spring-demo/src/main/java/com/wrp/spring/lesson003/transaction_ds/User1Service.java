package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 14:08
 **/
@Component
public class User1Service {
	@Autowired
	private JdbcTemplate jdbcTemplate1;

	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void required() {
		this.jdbcTemplate1.update("insert into public.user2(name) VALUES (?)", "李四");
	}

}