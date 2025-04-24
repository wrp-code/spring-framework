package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author wrp
 * @since 2025年04月14日 16:47
 **/
public class BeanNameTest {

	@Test
	public void test() {
		String beanXml = "classpath:/lesson001/beans.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(beanXml);
		for (int i = 1; i < 6; i++) {
			String beanName = "user" + i;
			String[] aliases = applicationContext.getAliases(beanName);
			System.out.printf("beanName:%s, 别名：[%s]%n", beanName, String.join(",", aliases));
		}

		System.out.println("spring容器中所有bean如下：");
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			String[] aliases = applicationContext.getAliases(beanName);
			System.out.printf("beanName:%s, 别名：[%s]%n", beanName, String.join(",", aliases));
		}
	}
}
