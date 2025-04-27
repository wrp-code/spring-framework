package com.wrp.spring.framework.enable.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-27 07:39
 **/
@Component
public class RechargeService {
	//模拟异步充值
	@Async(Config.RECHARGE_EXECUTORS_BEAN_NAME)
	public void recharge() {
		System.out.println(Thread.currentThread() + "模拟异步充值");
	}
}