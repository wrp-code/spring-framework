package com.wrp.spring.lesson003.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author wrp
 * @since 2025-04-27 21:14
 **/
public class TransactionTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext();
		context.register(TxConfig.class);
		context.refresh();
		DataSource dataSource = context.getBean(DataSource.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		//1.定义事务管理器
		PlatformTransactionManager transactionManager =
				new DataSourceTransactionManager(dataSource);
		//2.定义事务属性：TransactionDefinition，TransactionDefinition可以用来配置事务的属性信息，比如事务隔离级别、事务超时时间、事务传播方式、是否是只读事务等等。
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		// 3. 开启事务,得到事务状态(TransactionStatus)对象
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

		try {
			System.out.println("before:" + jdbcTemplate.queryForList("SELECT * from public.user"));
			jdbcTemplate.update("insert into public.user (name) values (?)", "test1-1");
			jdbcTemplate.update("insert into public.user (name) values (?)", "test1-2");
			transactionManager.commit(transactionStatus);
			System.out.println("提交事务...");
		} catch (Exception e) {
			System.out.println("回滚事务...");
			transactionManager.rollback(transactionStatus);
		}
		System.out.println("after:" + jdbcTemplate.queryForList("SELECT * from public.user"));
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext();
		context.register(TxConfig.class);
		context.refresh();
		DataSource dataSource = context.getBean(DataSource.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		//1.定义事务管理器
		PlatformTransactionManager transactionManager =
				new DataSourceTransactionManager(dataSource);
		//2.定义事务属性：TransactionDefinition，TransactionDefinition可以用来配置事务的属性信息，比如事务隔离级别、事务超时时间、事务传播方式、是否是只读事务等等。
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		// 3. 创建TransactionTemplate
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, transactionDefinition);
		/**
		 * 4.通过TransactionTemplate提供的方法执行业务操作
		 * 主要有2个方法：
		 * （1）.executeWithoutResult(Consumer<TransactionStatus> action)：没有返回值的，需传递一个Consumer对象，在accept方法中做业务操作
		 * （2）.<T> T execute(TransactionCallback<T> action)：有返回值的，需要传递一个TransactionCallback对象，在doInTransaction方法中做业务操作
		 * 调用execute方法或者executeWithoutResult方法执行完毕之后，事务管理器会自动提交事务或者回滚事务。
		 * 那么什么时候事务会回滚，有2种方式：
		 * （1）transactionStatus.setRollbackOnly();将事务状态标注为回滚状态
		 * （2）execute方法或者executeWithoutResult方法内部抛出异常
		 * 什么时候事务会提交？
		 * 方法没有异常 && 未调用过transactionStatus.setRollbackOnly();
		 */
		transactionTemplate.executeWithoutResult(status -> {
			jdbcTemplate.update("insert into public.user (name) values (?)", "test1-1");
			jdbcTemplate.update("insert into public.user (name) values (?)", "test1-2");
		});
		System.out.println("after:" + jdbcTemplate.queryForList("SELECT * from public.user"));
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
		UserService userService = context.getBean(UserService.class);
		userService.bus1();
		System.out.println(userService.userList());
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(TxConfig.class);
		context.refresh();

		UserService userService = context.getBean(UserService.class);
		userService.insertBatch("java高并发系列1", "mysql系列", "maven系列", "mybatis系列");
	}
}
