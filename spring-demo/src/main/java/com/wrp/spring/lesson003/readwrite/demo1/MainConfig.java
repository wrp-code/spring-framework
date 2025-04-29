package com.wrp.spring.lesson003.readwrite.demo1;

import com.wrp.spring.lesson003.readwrite.base.DsType;
import com.wrp.spring.lesson003.readwrite.base.EnableReadWrite;
import com.wrp.spring.lesson003.readwrite.base.ReadWriteDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月29日 14:52
 **/
@EnableReadWrite //@1
@Configuration
@ComponentScan
public class MainConfig {
	//主库数据源
	@Bean
	public DataSource masterDs() {
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test",
				"postgres",
				"wrp@PGatSMGI0601");
	}

	//从库数据源
	@Bean
	public DataSource slaveDs() {
		return new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/test",
				"postgres",
				"wrp@PGatSMGI0601");
	}

	//读写分离路由数据源
	@Bean
	public ReadWriteDataSource dataSource() {
		ReadWriteDataSource dataSource = new ReadWriteDataSource();
		//设置主库为默认的库，当路由的时候没有在datasource那个map中找到对应的数据源的时候，会使用这个默认的数据源
		dataSource.setDefaultTargetDataSource(this.masterDs());
		//设置多个目标库
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DsType.MASTER, this.masterDs());
		targetDataSources.put(DsType.SLAVE, this.slaveDs());
		dataSource.setTargetDataSources(targetDataSources);
		return dataSource;
	}

	//JdbcTemplate，dataSource为上面定义的注入读写分离的数据源
	@Bean
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	//定义事务管理器，dataSource为上面定义的注入读写分离的数据源
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}