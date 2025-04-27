package com.wrp.spring.lesson003.transaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wrp
 * @since 2025-04-27 23:06
 **/
public class PropagationTest {

	private TxService txService;
	private JdbcTemplate jdbcTemplate;

	//每个@Test用例执行之前先启动一下spring容器，并清理一下user1、user2中的数据
	@BeforeEach
	public void before() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
		txService = context.getBean(TxService.class);
		jdbcTemplate = context.getBean(JdbcTemplate.class);
		jdbcTemplate.update("delete from public.user1");
		jdbcTemplate.update("delete from public.user2");
	}

	@AfterEach
	public void after() {
		System.out.println("user1表数据：" + jdbcTemplate.queryForList("SELECT * from public.user1"));
		System.out.println("user2表数据：" + jdbcTemplate.queryForList("SELECT * from public.user2"));
	}

	@Test
	public void notransaction_exception_required_required() {
		txService.notransaction_exception_required_required();
	}

	@Test
	public void notransaction_required_required_exception() {
		txService.notransaction_required_required_exception();
	}

	@Test
	public void transaction_exception_required_required() {
		txService.transaction_exception_required_required();
	}

	@Test
	public void transaction_required_required_exception() {
		txService.transaction_required_required_exception();
	}

	@Test
	public void transaction_required_required_exception_try() {
		txService.transaction_required_required_exception_try();
	}
}
