package com.wrp.spring.framework.enable.async;

import com.wrp.spring.lesson003.async.MainConfig5;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-27 07:39
 **/
@Component
public class CashOutService {
	//模拟异步提现
	@Async(MainConfig5.CASHOUT_EXECUTORS_BEAN_NAME)
	public void cashOut() {
		System.out.println(Thread.currentThread() + "模拟异步提现");
	}
}