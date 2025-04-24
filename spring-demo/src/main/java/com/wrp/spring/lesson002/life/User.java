package com.wrp.spring.lesson002.life;

/**
 * @author wrp
 * @since 2025-04-21 21:20
 **/
public class User {
	private String name;

	private Car car;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", car=" + car +
				'}';
	}
}
