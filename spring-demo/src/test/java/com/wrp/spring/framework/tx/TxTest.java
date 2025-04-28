package com.wrp.spring.framework.tx;

import com.wrp.spring.lesson003.transaction.Service3;
import com.wrp.spring.lesson003.transaction.TxConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-28 22:52
 **/
public class TxTest {

	@Test
	public void test1() {
		var context = new AnnotationConfigApplicationContext(TxConfig.class);
		Service3 bean = context.getBean(Service3.class);
		bean.m1();
	}

	@Test
	public void test2() {
		var context = new AnnotationConfigApplicationContext(TxConfig.class);
		Service3 bean = context.getBean(Service3.class);
		bean.m2();
	}

	@Test
	public void test3() {
		var context = new AnnotationConfigApplicationContext(TxConfig.class);
		Service3 bean = context.getBean(Service3.class);
		bean.m3();
	}

	@Test
	public void test5() {
		var context = new AnnotationConfigApplicationContext(TxConfig.class);
		Service3 bean = context.getBean(Service3.class);
		bean.m5();
	}
}
