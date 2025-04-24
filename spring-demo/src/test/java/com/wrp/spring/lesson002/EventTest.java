package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.event.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

/**
 * @author wrp
 * @since 2025-04-23 22:00
 **/
public class EventTest {
	@Test
	public void test0() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
		//获取用户注册服务
		UserRegisterService userRegisterService =
				context.getBean(UserRegisterService.class);
		//模拟用户注册
		userRegisterService.registerUser("路人甲Java");
	}

	@Test
	public void test2() throws InterruptedException {
		//创建事件广播器
		ApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
		//注册事件监听器
		applicationEventMulticaster.addApplicationListener(new SendEmailOnOrderCreateListener());
		//广播事件订单创建事件
		applicationEventMulticaster.multicastEvent(new OrderCreateEvent(applicationEventMulticaster, 1L));
	}

	@Test
	public void test3() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig2.class);
		context.refresh();
		//获取用户注册服务
		UserRegisterService2 userRegisterService =
				context.getBean(UserRegisterService2.class);
		//模拟用户注册
		userRegisterService.registerUser("路人甲Java");
	}
}
