package com.wrp.spring.framework.enable.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author wrp
 * @since 2025年05月12日 14:08
 **/
//@Configuration
public class Config2 {

//	@Scheduled(cron = "0/1 * * * * ?")
	public void threadName() throws InterruptedException {
		System.out.println("config2: " + Thread.currentThread().getName());
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		// 定时任务执行线程池核心线程数
		taskScheduler.setPoolSize(4);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setThreadNamePrefix("MySchedulerThreadPool-");
		return taskScheduler;
	}
}
