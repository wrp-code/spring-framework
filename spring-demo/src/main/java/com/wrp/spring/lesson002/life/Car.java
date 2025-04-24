package com.wrp.spring.lesson002.life;

/**
 * @author wrp
 * @since 2025-04-21 21:17
 **/
public class Car {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Car{" +
				"name='" + name + '\'' +
				'}';
	}
}
