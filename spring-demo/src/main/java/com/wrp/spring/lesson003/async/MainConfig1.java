package com.wrp.spring.lesson003.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * @author wrp
 * @since 2025-04-26 08:12
 **/
@ComponentScan
@EnableAsync
public class MainConfig1 {

	// 名称必须是taskExecutor
//	@Bean
//	public Executor taskExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(10);
//		executor.setMaxPoolSize(100);
//		executor.setThreadNamePrefix("my-thread-");
//		return executor;
//	}

	@Bean
	public AsyncConfigurer asyncConfigurer(@Qualifier("logExecutors") Executor executor) {
		return new AsyncConfigurer() {
			@Override
			public Executor getAsyncExecutor() {
				return executor;
			}

			// 自定义异常处理
			@Override
			public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
				return new AsyncUncaughtExceptionHandler() {
					@Override
					public void handleUncaughtException(Throwable ex, Method method, Object... params) {
						String msg = String.format("方法[%s],参数[%s],发送异常了，异常详细信息:", method, Arrays.asList(params));
						System.out.println(msg);
						ex.printStackTrace();
					}
				};
			}
		};
	}

	/**
	 * 定义一个线程池，用来异步处理日志方法调用
	 *
	 */
	@Bean
	public Executor logExecutors() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(100);
		//线程名称前缀
		executor.setThreadNamePrefix("log-thread2-"); //@1
		return executor;
	}
}
