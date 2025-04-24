package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月15日 9:59
 **/
public class UserStaticFactory {
	/**
	 * 静态无参方法创建UserModel
	 *
	 */
	public static UserModel buildUser1() {

		System.out.println(UserStaticFactory.class + ".buildUser1()");

		UserModel userModel = new UserModel();
		userModel.setName("我是无参静态构造方法创建的!");
		return userModel;
	}

	/**
	 * 静态有参方法创建UserModel
	 *
	 * @param name 名称
	 * @param age  年龄
	 */
	public static UserModel buildUser2(String name, int age) {
		System.out.println(UserStaticFactory.class + ".buildUser2()");

		UserModel userModel = new UserModel();
		userModel.setName(name);
		userModel.setAge(age);
		return userModel;
	}
}
