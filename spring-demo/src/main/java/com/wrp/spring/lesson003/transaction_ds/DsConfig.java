package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author wrp
 * @since 2025年04月28日 12:16
 **/
@ComponentScan
@Configuration
@EnableTransactionManagement
public class DsConfig {

	//数据源1
	@Bean
	public DataSource dataSource1() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test",
				"postgres",
				"wrp@PGatSMGI0601");
	}

	//事务管理器1，对应数据源1
	@Bean
	public PlatformTransactionManager transactionManager1(@Qualifier("dataSource1")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1")DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	//数据源2
	@Bean
	public DataSource dataSource2() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test2",
				"postgres",
				"wrp@PGatSMGI0601");
	}

	//事务管理器2，对应数据源2
	@Bean
	public PlatformTransactionManager transactionManager2(@Qualifier("dataSource2")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2")DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	//事务管理器3，对应数据源1
	@Bean
	public PlatformTransactionManager transactionManager3(@Qualifier("dataSource1")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate3(@Qualifier("dataSource1")DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
