package com.wrp.spring.lesson001.javabean;

import org.springframework.context.annotation.Bean;

/**
 * 功能等同于一个bean xml
 * @author wrp
 * @since 2025年04月18日 12:17
 **/
public class ConfigBean1 {

	//bean名称为方法默认值：user1
	@Bean
	public User user1() {
		return new User();
	}

	//bean名称通过value指定了：user2Bean
	@Bean("user2Bean")
	public User user2() {
		return new User();
	}

	//bean名称为：user3Bean，2个别名：[user3BeanAlias1,user3BeanAlias2]
	@Bean({"user3Bean", "user3BeanAlias1", "user3BeanAlias2"})
	public User user3() {
		return new User();
	}
}
