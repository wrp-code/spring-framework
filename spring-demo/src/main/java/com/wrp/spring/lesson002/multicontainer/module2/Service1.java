package com.wrp.spring.lesson002.multicontainer.module2;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:14
 **/
@Component
public class Service1 {
	public String m2() {
		return "我是module2中的Servce1中的m2方法";
	}
}