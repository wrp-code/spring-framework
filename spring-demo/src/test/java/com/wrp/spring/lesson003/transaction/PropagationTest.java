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

	@Test
	public void notransaction_exception_requiresNew_requiresNew() {
		txService.notransaction_exception_requiresNew_requiresNew();
	}

	@Test
	public void notransaction_requiresNew_requiresNew_exception() {
		txService.notransaction_requiresNew_requiresNew_exception();
	}

	@Test
	public void transaction_exception_required_requiresNew_requiresNew() {
		txService.transaction_exception_required_requiresNew_requiresNew();
	}

	@Test
	public void transaction_required_requiresNew_requiresNew_exception() {
		txService.transaction_required_requiresNew_requiresNew_exception();
	}

	@Test
	public void transaction_required_requiresNew_requiresNew_exception_try() {
		txService.transaction_required_requiresNew_requiresNew_exception_try();
	}

	@Test
	public void notransaction_exception_nested_nested() {
		txService.notransaction_exception_nested_nested();
	}

	@Test
	public void notransaction_nested_nested_exception() {
		txService.notransaction_nested_nested_exception();
	}

	@Test
	public void transaction_exception_nested_nested() {
		txService.transaction_exception_nested_nested();
	}

	@Test
	public void transaction_nested_nested_exception() {
		txService.transaction_nested_nested_exception();
	}

	@Test
	public void transaction_nested_nested_exception_try() {
		txService.transaction_nested_nested_exception_try();
	}

	@Test
	public void notransaction_exception_supports_supports() {
		txService.notransaction_exception_supports_supports();
	}

	@Test
	public void notransaction_supports_supports_exception() {
		txService.notransaction_supports_supports_exception();
	}

	@Test
	public void transaction_exception_supports_supports() {
		txService.transaction_exception_supports_supports();
	}

	@Test
	public void transaction_supports_supports_exception() {
		txService.transaction_supports_supports_exception();
	}

	@Test
	public void transaction_supports_supports_exception_try() {
		txService.transaction_supports_supports_exception_try();
	}

	@Test
	public void notransaction_exception_mandatory_mandatory() {
		txService.notransaction_exception_mandatory_mandatory();
	}

	@Test
	public void notransaction_mandatory_mandatory_exception() {
		txService.notransaction_mandatory_mandatory_exception();
	}

	@Test
	public void transaction_exception_mandatory_mandatory() {
		txService.transaction_exception_mandatory_mandatory();
	}

	@Test
	public void transaction_mandatory_mandatory_exception() {
		txService.transaction_mandatory_mandatory_exception();
	}

	@Test
	public void transaction_mandatory_mandatory_exception_try() {
		txService.transaction_mandatory_mandatory_exception_try();
	}

	@Test
	public void notransaction_exception_not_supported_not_supported() {
		txService.notransaction_exception_not_supported_not_supported();
	}

	@Test
	public void notransaction_not_supported_not_supported_exception() {
		txService.notransaction_not_supported_not_supported_exception();
	}

	@Test
	public void transaction_exception_not_supported_not_supported() {
		txService.transaction_exception_not_supported_not_supported();
	}

	@Test
	public void transaction_not_supported_not_supported_exception() {
		txService.transaction_not_supported_not_supported_exception();
	}

	@Test
	public void transaction_not_supported_not_supported_exception_try() {
		txService.transaction_not_supported_not_supported_exception_try();
	}

	@Test
	public void notransaction_exception_never_never() {
		txService.notransaction_exception_never_never();
	}

	@Test
	public void notransaction_never_never_exception() {
		txService.notransaction_never_never_exception();
	}

	@Test
	public void transaction_exception_never_never() {
		txService.transaction_exception_never_never();
	}

	@Test
	public void transaction_never_never_exception() {
		txService.transaction_never_never_exception();
	}

	@Test
	public void transaction_never_never_exception_try() {
		txService.transaction_never_never_exception_try();
	}

}
