package com.wrp.spring.lesson001.beanfactory;

import lombok.Data;

/**
 * @author wrp
 * @since 2025年04月15日 12:16
 **/
@Data
public class CarModel {

	private String brand;
	private String desc;

	public CarModel(String brand, String desc) {
		this.brand = brand;
		this.desc = desc;
	}
}
