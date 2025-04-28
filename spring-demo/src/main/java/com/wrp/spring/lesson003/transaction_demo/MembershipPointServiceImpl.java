package com.wrp.spring.lesson003.transaction_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wrp
 * @since 2025-04-28 08:10
 **/
@Service
public class MembershipPointServiceImpl {

	@Autowired
	RecordServiceImpl recordService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 外事事务的子事务
	@Transactional(propagation = Propagation.NESTED)
	public void addPoint(Object point) {
		System.out.println("添加积分");
		this.jdbcTemplate.update("insert into public.point(name) VALUES ('添加积分')");
		try {
			recordService.addRecord(null);
		} catch (Exception e) {
			// ，，，捕获异常，事务就感知不到异常，事务不会回滚
		}

		// 本事务回滚，向外围方法抛出异常，如果外围catch异常了，则不影响外围事务
		throw new RuntimeException();
	}
}
