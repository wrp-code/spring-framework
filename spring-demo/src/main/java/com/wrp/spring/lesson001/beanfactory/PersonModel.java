package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月15日 12:16
 **/
public class PersonModel {
	private UserModel userModel;
	private CarModel carModel;

	public PersonModel() {
	}

	public PersonModel(UserModel userModel, CarModel carModel) {
		this.userModel = userModel;
		this.carModel = carModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}

	@Override
	public String toString() {
		return "PersonModel{" +
				"userModel=" + userModel +
				", carModel=" + carModel +
				'}';
	}
}
