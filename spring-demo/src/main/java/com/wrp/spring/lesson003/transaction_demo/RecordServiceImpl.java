package com.wrp.spring.lesson003.transaction_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025-04-28 08:14
 **/
@Service
public class RecordServiceImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 以非事务方式执行操作
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void addRecord(Object record) {
		System.out.println("记录日志");
		this.jdbcTemplate.update("insert into public.log_record(name) VALUES ('添加日志记录')");
		throw new RuntimeException();
	}
}
