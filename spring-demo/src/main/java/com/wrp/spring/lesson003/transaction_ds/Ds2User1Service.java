package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 13:49
 **/
@Component
public class Ds2User1Service {
	@Autowired
	private JdbcTemplate jdbcTemplate2;

	@Transactional(transactionManager = "transactionManager2", propagation = Propagation.REQUIRED)
	public void required(String name) {
		this.jdbcTemplate2.update("insert into public.user1(name) VALUES (?)", name);
	}

}