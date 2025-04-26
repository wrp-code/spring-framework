package com.wrp.spring.framework.beandefinition;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author wrp
 * @since 2025-04-26 16:01
 **/
public class UserFactoryBean implements FactoryBean<User> {
	@Override
	public User getObject() throws Exception {
		System.out.println("FactoryBean方式创建User");
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
