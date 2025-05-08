package com.wrp.spring.framework.lookup;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月08日 19:37
 **/
public class LookupTest {

	@Test
	public void test() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);

		context.getBean(B.class).print();
		context.getBean(B.class).print();
	}
}
