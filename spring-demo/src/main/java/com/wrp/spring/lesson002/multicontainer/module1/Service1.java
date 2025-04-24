package com.wrp.spring.lesson002.multicontainer.module1;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:13
 **/
@Component
public class Service1 {
	public String m1() {
		return "我是module1中的Servce1中的m1方法";
	}
}