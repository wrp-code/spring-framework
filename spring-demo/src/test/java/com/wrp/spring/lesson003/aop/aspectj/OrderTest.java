package com.wrp.spring.lesson003.aop.aspectj;

import com.wrp.spring.lesson003.aop.order.MainConfig2;
import com.wrp.spring.lesson003.aop.order.Service2;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-25 22:31
 **/
public class OrderTest {

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig2.class);
		context.refresh();
		Service2 service2 = context.getBean(Service2.class);
		service2.say("路人");
	}
}
