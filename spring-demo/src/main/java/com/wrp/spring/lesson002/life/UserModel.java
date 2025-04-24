package com.wrp.spring.lesson002.life;

/**
 * @author wrp
 * @since 2025-04-21 23:12
 **/
public class UserModel {
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
