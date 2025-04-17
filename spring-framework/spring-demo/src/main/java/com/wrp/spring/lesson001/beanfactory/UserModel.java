package com.wrp.spring.lesson001.beanfactory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wrp
 * @since 2025年04月14日 16:48
 **/
@Setter
@Getter
public class UserModel {

	private String name;
	private int age;
	//描述信息
	private String desc;

	public UserModel() {
		this.name = "我是通过UserModel的无参构造方法创建的!";
	}

	public UserModel(String name, int age) {
		this.name = name;
		this.age = age;
	}

	// 防止编译后参数名称丢失
//	@ConstructorProperties({"name", "desc"})
	public UserModel(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public UserModel(String name, int age, String desc) {
		this.name = name;
		this.age = age;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"name='" + name + '\'' +
				", age=" + age +
				", desc='" + desc + '\'' +
				'}';
	}
}
