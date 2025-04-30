package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月30日 22:13
 */
public class PostProcessBeforeInitializationTest {
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(Bean1.class);
		context.refresh();
	}
}
