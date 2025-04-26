package com.wrp.spring.lesson003.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-26 08:23
 **/
@Component
public class RechargeService {
	//模拟异步充值
	@Async(MainConfig5.RECHARGE_EXECUTORS_BEAN_NAME)
	public void recharge() {
		System.out.println(Thread.currentThread() + "模拟异步充值");
	}
}