package com.wrp.spring.framework.enable.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author wrp
 * @since 2025年05月11日 8:34
 */
@Configuration
@EnableScheduling
public class Config {

	@Scheduled(cron = "0/3 * * * * ?")
	public void threadName() {
		System.out.println(Thread.currentThread().getName());
	}
}
