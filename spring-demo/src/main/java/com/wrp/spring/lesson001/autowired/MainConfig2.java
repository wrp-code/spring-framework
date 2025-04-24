package com.wrp.spring.lesson001.autowired;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author wrp
 * @since 2025-04-21 20:30
 **/
@Configurable
public class MainConfig2 {
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ServiceA serviceA() {
		return new ServiceA();
	}
}