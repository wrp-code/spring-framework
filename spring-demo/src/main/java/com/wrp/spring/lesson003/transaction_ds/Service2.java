package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 12:29
 **/
@Service
public class Service2 {
	@Autowired
	JdbcTemplate jdbcTemplate1;

	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void m2(){
		this.jdbcTemplate1.update("insert into public.user1(name) VALUES ('李四')");
	}
}
