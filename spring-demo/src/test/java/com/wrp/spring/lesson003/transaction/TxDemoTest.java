package com.wrp.spring.lesson003.transaction;

import com.wrp.spring.lesson003.transaction.demo.DemoConfig;
import com.wrp.spring.lesson003.transaction.demo.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wrp
 * @since 2025-04-28 08:25
 **/
public class TxDemoTest {



	@Test
	public void test() {
		AnnotationConfigApplicationContext context=
				new AnnotationConfigApplicationContext(DemoConfig.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		jdbcTemplate.update("delete from public.user");
		jdbcTemplate.update("delete from public.point");
		jdbcTemplate.update("delete from public.log_record");

		UserServiceImpl bean = context.getBean(UserServiceImpl.class);
		bean.register(null);
	}
}
