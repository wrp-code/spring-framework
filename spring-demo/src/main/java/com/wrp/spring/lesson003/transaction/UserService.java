package com.wrp.spring.lesson003.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author wrp
 * @since 2025-04-27 21:59
 **/
@Component
public class UserService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TransactionTemplate transactionTemplate;

	//模拟业务操作1
	public void bus1() {
		this.transactionTemplate.executeWithoutResult(transactionStatus -> {
			//先删除表数据
			this.jdbcTemplate.update("delete from public.user");
			//调用bus2
			this.bus2();
		});
	}

	//模拟业务操作2
	public void bus2() {
		this.transactionTemplate.executeWithoutResult(transactionStatus -> {
			this.jdbcTemplate.update("insert into public.user (name) values (?)", "java");
			this.jdbcTemplate.update("insert into public.user (name) values (?)", "spring");
			this.jdbcTemplate.update("insert into public.user (name) values (?)", "mybatis");
		});
	}

	//查询表中所有数据
	public List userList() {
		return jdbcTemplate.queryForList("select * from public.user");
	}

	// @Transactional只对public方法有效
	@Transactional
	public void insert(String userName){
		this.jdbcTemplate.update("insert into public.user (name) values (?)", userName);
	}

	//先清空表中数据，然后批量插入数据，要么都成功要么都失败
	@Transactional
	public void insertBatch(String... names) {
		jdbcTemplate.update("delete from public.user");
		for (String name : names) {
			jdbcTemplate.update("INSERT INTO public.user(name) VALUES (?)", name);
		}
	}
}