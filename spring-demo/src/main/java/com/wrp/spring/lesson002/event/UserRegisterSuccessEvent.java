package com.wrp.spring.lesson002.event;

/**
 * @author wrp
 * @since 2025-04-23 21:56
 **/
public class UserRegisterSuccessEvent  extends AbstractEvent {
	//用户名
	private String userName;

	/**
	 * 创建用户注册成功事件对象
	 *
	 * @param source   事件源
	 * @param userName 当前注册的用户名
	 */
	public UserRegisterSuccessEvent(Object source, String userName) {
		super(source);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}