package com.wrp.spring.lesson002.multicontainer.module2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:14
 **/
@Component
public class Service3 {
	//使用模块2中的Service1
	@Autowired
	private com.wrp.spring.lesson002.multicontainer.module2.Service1 service1; //@1
	//使用模块1中的Service2
	@Autowired
	private com.wrp.spring.lesson002.multicontainer.module1.Service2 service2; //@2

	public String m1() {
		return this.service2.m1();
	}

	public String m2() {
		return this.service1.m2();
	}

}