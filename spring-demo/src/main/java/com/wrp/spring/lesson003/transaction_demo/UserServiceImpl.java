package com.wrp.spring.lesson003.transaction_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 案例：注册时添加积分，添加积分出错不影响注册
 * @author wrp
 * @since 2025-04-28 08:18
 **/
@Service
public class UserServiceImpl {

	@Autowired
	MembershipPointServiceImpl membershipPointService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	// 开启事务，默认是Propagation.Request
	@Transactional
	public void register(Object user) {
		System.out.println("注册用户");
		this.jdbcTemplate.update("insert into public.user(name) VALUES ('注册用户')");
		try {
			membershipPointService.addPoint(null);
		} catch (Exception e) {
			// ，，，捕获异常，事务就感知不到异常，事务不会回滚
		}
		// 会影响到子事务
//		throw new RuntimeException();
	}
}
