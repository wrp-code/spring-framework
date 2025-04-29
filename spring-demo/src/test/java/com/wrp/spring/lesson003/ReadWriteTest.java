package com.wrp.spring.lesson003;

import com.wrp.spring.lesson003.readwrite.base.DsType;
import com.wrp.spring.lesson003.readwrite.demo1.MainConfig;
import com.wrp.spring.lesson003.readwrite.demo1.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月29日 14:56
 **/
public class ReadWriteTest {
	UserService userService;

	@BeforeEach
	public void before() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig.class);
		context.refresh();
		this.userService = context.getBean(UserService.class);
	}

	@Test
	public void test1() {
		System.out.println(this.userService.getUserNameById(1, DsType.MASTER));
		System.out.println(this.userService.getUserNameById(1, DsType.SLAVE));
	}

	@Test
	public void test2() {
		long id = System.currentTimeMillis();
		System.out.println(id);
		this.userService.insert(id, "张三");
	}
}
