package com.wrp.spring.lesson002.life.callback;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 12:09
 **/
@Component
public class MySmartInitializingSingleton implements SmartInitializingSingleton {
	@Override
	public void afterSingletonsInstantiated() {
		System.out.println("所有bean初始化完毕！");
	}
}