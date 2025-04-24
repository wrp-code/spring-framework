package com.wrp.spring.lesson002.beanfactoryprocessor;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-24 22:43
 **/
@Component
public class LessonModel {
	//课程名称
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "LessonModel{" +
				"name='" + name + '\'' +
				'}';
	}
}