package com.wrp.spring.lesson003.transaction;

import com.wrp.spring.lesson003.transaction_order.TxOrderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wrp
 * @since 2025-04-29 07:13
 **/
public class TxOrderTest {

	private com.wrp.spring.lesson003.transaction_order.UserService userService;

	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void before() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxOrderConfig.class);
		userService = context.getBean(com.wrp.spring.lesson003.transaction_order.UserService.class);
		this.jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
		jdbcTemplate.update("delete from public.user1");
	}

	@Test
	public void test1() {
		this.userService.addUser();
	}
}
