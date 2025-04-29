package com.wrp.spring.lesson003.tx_business;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author wrp
 * @since 2025-04-29 08:05
 **/
@ComponentScan
@EnableTransactionManagement
public class TxBusinessConfig {

	@Bean
	public DataSource dataSource() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test",
				"postgres",
				"123456");
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
