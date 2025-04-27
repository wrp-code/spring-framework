package com.wrp.spring.lesson003.aop;

import com.wrp.spring.lesson003.scheduled.MainConfig1;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-27 08:07
 **/
public class ScheduledTest {

	@Test
	public void test1() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		//休眠一段时间，房子junit自动退出
		TimeUnit.SECONDS.sleep(10000);
	}
}
