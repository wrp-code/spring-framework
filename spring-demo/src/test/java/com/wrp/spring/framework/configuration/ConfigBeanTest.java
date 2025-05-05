package com.wrp.spring.framework.configuration;

import com.wrp.spring.framework.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月04日 13:30
 */
public class ConfigBeanTest {

	@Test
	public void test() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ConfigBean.class);
		context.refresh();

		Assertions.assertNotNull(context.getBean(User.class));
	}
}
