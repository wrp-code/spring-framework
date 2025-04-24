package com.wrp.spring.lesson002.multicontainer.module1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:14
 **/
@Component
public class Service2 {

	@Autowired
	private Service1 service1; //@1

	public String m1() { //@2
		return this.service1.m1();
	}

}