package com.wrp.spring.lesson003.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-27 08:06
 **/
@Component
public class PushJob {

	//推送方法，每秒执行一次
	@Scheduled(fixedRate = 1000)
	public void push() throws InterruptedException {
		System.out.println("模拟推送消息，" + System.currentTimeMillis());
	}
}
