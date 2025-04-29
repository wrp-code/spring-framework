package com.wrp.spring.lesson003.factorylife;

import com.wrp.spring.lesson003.readwrite.demo1.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月29日 15:24
 **/
public class Config {

	public static void main(String[] args) {
		//1.创建spring上下文
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
		//2.上下文中注册bean
		configApplicationContext.register(MainConfig.class);
		//3.刷新spring上下文，内部会启动spring上下文
		configApplicationContext.refresh();
		//4.关闭spring上下文
		System.out.println("stop ok!");
	}
}
