package com.wrp.spring.lesson002.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-23 22:40
 **/
@Component
public class UserRegisterListener {

	@EventListener
	@Order(11)
	public void sendMail(UserRegisterEvent event) {
		System.out.println(String.format(Thread.currentThread().getName() + "给用户【%s】发送注册成功邮件!", event.getUserName()));
	}

	@EventListener
	@Order(1)
	public void sendCompon(UserRegisterEvent event) {
		System.out.println(String.format(Thread.currentThread().getName() + "给用户【%s】发送优惠券!", event.getUserName()));
	}
}