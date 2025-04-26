package com.wrp.spring.lesson003.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-26 08:12
 **/
@Component
public class LogService {
	@Async
	public void log(String msg) throws InterruptedException {
		System.out.println(Thread.currentThread() + "开始记录日志," + System.currentTimeMillis());
		//模拟耗时2秒
		TimeUnit.SECONDS.sleep(2);
		System.out.println(Thread.currentThread() + "日志记录完毕," + System.currentTimeMillis());
	}

	@Async
	public Future<String> mockException() {
		//模拟抛出一个异常
		throw new IllegalArgumentException("参数有误!");
	}

	@Async
	public void mockNoReturnException() {
		//模拟抛出一个异常
		throw new IllegalArgumentException("无返回值的异常!");
	}
}