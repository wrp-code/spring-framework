package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author wrp
 * @since 2025-04-21 23:33
 **/
public class InvokeAwareTest {
	@Test
	public void test1() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		factory.registerBeanDefinition("awareBean", BeanDefinitionBuilder.genericBeanDefinition(AwareBean.class).getBeanDefinition());
		//调用getBean方法获取bean，将触发bean的初始化
		factory.getBean("awareBean");
	}
}
