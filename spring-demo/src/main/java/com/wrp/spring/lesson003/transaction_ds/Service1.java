package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 12:28
 **/
@Service
public class Service1 {

	@Autowired
	JdbcTemplate jdbcTemplate1;
	@Autowired
	Service2 service2;

	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void m1(){
		this.jdbcTemplate1.update("insert into public.user1(name) VALUES ('张三')");
		service2.m2();
	}
}
