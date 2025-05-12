package com.wrp.spring.framework.enable.schedule;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025年05月11日 8:34
 */
@ComponentScan
@Configuration
@EnableScheduling // @Import(SchedulingConfiguration.class) ==> ScheduledAnnotationBeanPostProcessor
public class Config {

//	@Scheduled(cron = "0/1 * * * * ?")
//	public void task0() throws InterruptedException {
//		System.out.println("config: " + Thread.currentThread().getName());
//	}

	int count = 0;

//	@Scheduled(fixedRate = 3000)
//	public void task1() throws InterruptedException {
//		System.out.println("fixedRate = 3000, " + System.currentTimeMillis());
//	}

//	@Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
//	public void task2() {
//		System.out.println("fixedRate = 3, " + System.currentTimeMillis());
//	}

//	@Scheduled(fixedDelay = 5000)
//	public void task3() {
//		System.out.println("fixedDelay = 5000, "+ System.currentTimeMillis());
//	}

//	@Scheduled(initialDelay = 2000)
//	public void task4() {
//		System.out.println("@Scheduled");
//	}

	@Scheduled(fixedRate = 3000)
	public void task5() throws InterruptedException {
		if(count == 3 || count == 4) {
			TimeUnit.SECONDS.sleep(10);
		}
		System.out.println("fixedRate = 3000, " + System.currentTimeMillis() + " count: " + count++);
	}

//	@Scheduled(fixedDelay = 1000)
//	public void task6() throws InterruptedException {
//		if(count == 3) {
//			TimeUnit.SECONDS.sleep(10);
//		}
//		System.out.println("fixedDelay = 1000, "+ System.currentTimeMillis() + " count: " + count++);
//	}
}
