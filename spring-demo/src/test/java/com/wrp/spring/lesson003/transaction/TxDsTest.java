package com.wrp.spring.lesson003.transaction;

import com.wrp.spring.lesson003.transaction_ds.DsConfig;
import com.wrp.spring.lesson003.transaction_ds.Tx1Service;
import com.wrp.spring.lesson003.transaction_ds.User3Service;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wrp
 * @since 2025年04月28日 12:31
 **/
public class TxDsTest {

	private Tx1Service txService1;
	private JdbcTemplate jdbcTemplate1;
	private JdbcTemplate jdbcTemplate2;
	private User3Service user3Service;

	//@Before标注的方法会在任意@Test方法执行之前执行，我们这在里清理一下2库中4张表的数据
	@BeforeEach
	public void before() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DsConfig.class);
		txService1 = context.getBean(Tx1Service.class);
		this.jdbcTemplate1 = context.getBean("jdbcTemplate1", JdbcTemplate.class);
		this.jdbcTemplate2 = context.getBean("jdbcTemplate2", JdbcTemplate.class);
		user3Service = context.getBean(User3Service.class);
		jdbcTemplate1.update("delete from public.user1");
		jdbcTemplate1.update("delete from public.user2");
		jdbcTemplate2.update("delete from public.user1");
		jdbcTemplate2.update("delete from public.user2");
	}

	//@After标注的方法会在任意@Test方法执行完毕之后执行，我们在此处输出4张表的数据，用来查看测试案例之后，表中的数据清空
	@AfterEach
	public void after() {
		System.out.println("ds1.user1表数据：" + this.jdbcTemplate1.queryForList("SELECT * from public.user1"));
		System.out.println("ds1.user2表数据：" + this.jdbcTemplate1.queryForList("SELECT * from public.user2"));
		System.out.println("ds2.user1表数据：" + this.jdbcTemplate2.queryForList("SELECT * from public.user1"));
		System.out.println("ds2.user2表数据：" + this.jdbcTemplate2.queryForList("SELECT * from public.user2"));
	}

	@Test
	public void test() {
		var context = new AnnotationConfigApplicationContext(DsConfig.class);
		var service1 = context.getBean(com.wrp.spring.lesson003.transaction_ds.Service1.class);
		service1.m1();
	}

	@Test
	public void test1() {
		this.txService1.test1();
	}

	@Test
	public void test2() {
		this.txService1.test2();
	}

	@Test
	public void test3() {
		this.txService1.test3();
	}

	@Test
	public void test4() {
		this.txService1.test4();
	}

	@Test
	public void test5() {
		this.user3Service.required();
	}
}
