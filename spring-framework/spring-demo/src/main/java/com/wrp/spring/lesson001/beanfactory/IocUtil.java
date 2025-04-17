package com.wrp.spring.lesson001.beanfactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月15日 11:59
 **/
public class IocUtil {
	public static ClassPathXmlApplicationContext context(String beanXml) {
		return new ClassPathXmlApplicationContext(beanXml);
	}
}
