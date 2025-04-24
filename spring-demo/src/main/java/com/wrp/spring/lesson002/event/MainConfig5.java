package com.wrp.spring.lesson002.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.Executor;

/**
 * @author wrp
 * @since 2025-04-23 22:59
 **/
@ComponentScan
@Configuration
public class MainConfig5 {
	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster() { //@1
		//创建一个事件广播器
		SimpleApplicationEventMulticaster result = new SimpleApplicationEventMulticaster();
		//给广播器提供一个线程池，通过这个线程池来调用事件监听器
		Executor executor = this.applicationEventMulticasterThreadPool().getObject();
		//设置异步执行器
		result.setTaskExecutor(executor);//@1
		return result;
	}

	@Bean
	public ThreadPoolExecutorFactoryBean applicationEventMulticasterThreadPool() {
		ThreadPoolExecutorFactoryBean result = new ThreadPoolExecutorFactoryBean();
		result.setThreadNamePrefix("applicationEventMulticasterThreadPool-");
		result.setCorePoolSize(5);
		return result;
	}
}