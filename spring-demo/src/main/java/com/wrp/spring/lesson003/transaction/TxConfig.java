package com.wrp.spring.lesson003.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author wrp
 * @since 2025-04-27 20:58
 **/
@ComponentScan
@Configuration
// 开启声明式事务
@EnableTransactionManagement
//@PropertySource({"classpath:lesson003/database.properties"})
public class TxConfig {

	@Bean
	public DataSource dataSource(DataSourceProperties dataSourceProperties) throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test",
				"postgres",
				"wrp@PGatSMGI0601");
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 简化事务
	@Bean
	public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
		return new TransactionTemplate(transactionManager);
	}
}
