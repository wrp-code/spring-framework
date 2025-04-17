package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月15日 10:02
 **/
public class UserFactory {
	public UserModel buildUser1() {
		System.out.println("----------------------1");
		UserModel userModel = new UserModel();
		userModel.setName("bean实例方法创建的对象!");
		return userModel;
	}

	public UserModel buildUser2(String name, int age) {
		System.out.println("----------------------2");
		UserModel userModel = new UserModel();
		userModel.setName(name);
		userModel.setAge(age);
		return userModel;
	}
}
