package com.wrp.spring.lesson003.transaction;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-27 20:55
 **/
@Data
@Component
public class DataSourceProperties {

	@Value("${datasource.driver-class-name}")
	private String driverClassName;
	@Value("${datasource.url}")
	private String url;
	@Value("${datasource.username}")
	private String username;
	@Value("${datasource.password}")
	private String password;

}
