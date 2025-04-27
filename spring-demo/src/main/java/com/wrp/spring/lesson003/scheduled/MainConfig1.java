package com.wrp.spring.lesson003.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author wrp
 * @since 2025-04-27 08:07
 **/
@ComponentScan
@EnableScheduling //在spring容器中启用定时任务的执行
public class MainConfig1 {

	@Bean
	public ScheduledExecutorService scheduledExecutorService() {
		return Executors.newScheduledThreadPool(20);
	}
}