package com.wrp.spring.lesson001.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:36
 **/
@Component
@Lazy //@1
public class Service1 {
	public Service1() {
		System.out.println("创建Service1");
	}
}
