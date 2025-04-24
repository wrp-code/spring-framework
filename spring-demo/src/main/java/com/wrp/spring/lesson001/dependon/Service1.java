package com.wrp.spring.lesson001.dependon;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:31
 **/
@DependsOn({"service2", "service3"}) //@1
@Component
public class Service1 {
	public Service1() {
		System.out.println("create Service1");
	}
}