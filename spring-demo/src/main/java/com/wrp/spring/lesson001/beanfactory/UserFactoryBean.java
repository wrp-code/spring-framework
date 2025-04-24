package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author wrp
 * @since 2025年04月15日 10:04
 **/
public class UserFactoryBean implements FactoryBean<UserModel> {
	int count = 1;
	@Override
	public UserModel getObject() throws Exception { //@1
		UserModel userModel = new UserModel();
		userModel.setName("我是通过FactoryBean创建的第"+count+++ "对象");//@4
		return userModel;
	}

	@Override
	public Class<?> getObjectType() {
		return UserModel.class; //@2
	}

	@Override
	public boolean isSingleton() {
//		return true; //@3
		return false; //@3
	}
}