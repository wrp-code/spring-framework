package com.wrp.spring.framework.primary;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月05日 18:04
 */
public class PrimaryTest {

	@Test
	public void test() {
		new AnnotationConfigApplicationContext(Config.class);

	}
}
