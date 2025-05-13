package com.wrp.spring.framework.enable.schedule;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author wrp
 * @since 2025-05-13 21:57
 **/
//@Configuration
public class MySchedulingConfigurer implements SchedulingConfigurer {
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(4);
		threadPoolTaskScheduler.setThreadNamePrefix("scheduling-");
		threadPoolTaskScheduler.initialize();
		taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
		taskRegistrar.addCronTask(()-> System.out.println("hello"),
				"*/1 * * * * ?");
		taskRegistrar.destroy();
	}
}
