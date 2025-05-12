package com.wrp.spring.framework.enable.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author wrp
 * @since 2025年05月12日 14:08
 **/
@Configuration
public class Config2 {

	@Scheduled(cron = "0/1 * * * * ?")
	public void threadName() throws InterruptedException {
		System.out.println("config2: " + Thread.currentThread().getName());
	}
}
