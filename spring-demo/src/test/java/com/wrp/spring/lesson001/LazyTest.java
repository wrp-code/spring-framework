package com.wrp.spring.lesson001;

import com.wrp.spring.lesson001.lazy.MainConfig7;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author wrp
 * @since 2025-04-21 20:37
 **/
public class LazyTest {

	@Test
	public void test7() {
		System.out.println("准备启动spring容器");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
		System.out.println("spring容器启动完毕");

		for (String beanName : Arrays.asList("name", "age", "address")) {
			System.out.println("----------");
			System.out.println("getBean:" + beanName + ",start");
			System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
			System.out.println("getBean:" + beanName + ",end");
		}
	}
}
