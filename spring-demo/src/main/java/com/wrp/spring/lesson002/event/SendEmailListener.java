package com.wrp.spring.lesson002.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 22:21
 **/
@Component
public class SendEmailListener implements ApplicationListener<UserRegisterEvent> {

	@Override
	public void onApplicationEvent(UserRegisterEvent event) {
		System.out.println(String.format("给用户【%s】发送注册成功邮件!", event.getUserName()));

	}
}