package com.wrp.spring.framework.enable.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-27 07:25
 **/
@Component
public class LogService {

	public void log() {
		System.out.println("开始日志记录...");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("结束日志记录...");
	}
}
