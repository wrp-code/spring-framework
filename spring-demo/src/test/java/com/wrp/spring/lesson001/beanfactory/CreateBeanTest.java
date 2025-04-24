package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过反射调用构造方法创建bean对象
 *
 * 通过静态工厂方法创建bean对象
 *
 * 通过实例工厂方法创建bean对象
 *
 * 通过FactoryBean创建bean对象
 * @author wrp
 * @since 2025年04月15日 9:55
 **/
public class CreateBeanTest {

	@Test
	public void create() {
		//1.bean配置文件位置
		String beanXml = "classpath:lesson001/create-beans.xml";

		//2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

		System.out.println("spring容器中所有bean如下：");

		//getBeanDefinitionNames用于获取容器中所有bean的名称
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + ":" + context.getBean(beanName));
		}
	}

	@Test
	public void createByFactoryBean() {
		//1.bean配置文件位置
		String beanXml = "classpath:lesson001/create-beans.xml";

		//2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

		System.out.println("spring容器中所有bean如下：");

		//getBeanDefinitionNames用于获取容器中所有bean的名称
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + ":" + context.getBean(beanName));
		}
		System.out.println("--------------------------");
		//多次获取createByFactoryBean看看是否是同一个对象
		System.out.println("createByFactoryBean:" + context.getBean("createByFactoryBean"));
		System.out.println("createByFactoryBean:" + context.getBean("createByFactoryBean"));
	}
}
