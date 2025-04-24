package com.wrp.spring.lesson002.life;

/**
 * @author wrp
 * @since 2025-04-21 22:45
 **/
public class Person {
	private String name;
	private Integer age;

	public Person() {
		System.out.println("调用 Person()");
	}

	@MyAutowired
	public Person(String name) {
		System.out.println("调用 Person(String name)");
		this.name = name;
	}

	public Person(String name, Integer age) {
		System.out.println("调用 Person(String name, int age)");
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}