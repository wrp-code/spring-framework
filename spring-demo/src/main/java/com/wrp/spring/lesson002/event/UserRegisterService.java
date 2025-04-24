package com.wrp.spring.lesson002.event;

/**
 * @author wrp
 * @since 2025-04-23 21:58
 **/
public class UserRegisterService {
	//事件发布者
	private EventMulticaster eventMulticaster; //@0

	/**
	 * 注册用户
	 *
	 * @param userName 用户名
	 */
	public void registerUser(String userName) { //@1
		//用户注册(将用户信息入库等操作)
		System.out.println(String.format("用户【%s】注册成功", userName)); //@2
		//广播事件
		this.eventMulticaster.multicastEvent(new UserRegisterSuccessEvent(this, userName)); //@3
	}

	public EventMulticaster getEventMulticaster() {
		return eventMulticaster;
	}

	public void setEventMulticaster(EventMulticaster eventMulticaster) {
		this.eventMulticaster = eventMulticaster;
	}
}
