package com.wrp.spring.lesson002.event;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 22:10
 **/
@Component
public class SendEmailOnUserRegisterSuccessListener implements EventListener<UserRegisterSuccessEvent> {
	@Override
	public void onEvent(UserRegisterSuccessEvent event) {
		System.out.println(
				String.format("给用户【%s】发送注册成功邮件!", event.getUserName()));
	}
}