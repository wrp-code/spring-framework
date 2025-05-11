package com.wrp.spring.framework.enable.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025年05月11日 8:33
 */
public class ScheduleTest {

	@Test
	public void test1() throws InterruptedException {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);

		TimeUnit.MINUTES.sleep(1);
	}
}
