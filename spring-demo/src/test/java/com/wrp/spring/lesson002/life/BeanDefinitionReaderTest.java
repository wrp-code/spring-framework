package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 阶段二、BeanDefinition解析阶段
 * @author wrp
 * @since 2025-04-21 21:29
 **/
public class BeanDefinitionReaderTest {
	@Test
	public void test3() {
		//定义一个spring容器，这个容器默认实现了BeanDefinitionRegistry，所以本身就是一个bean注册器
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//定义一个注解方式的BeanDefinition读取器，需要传递一个BeanDefinitionRegistry（bean注册器）对象
		AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(factory);

		//通过PropertiesBeanDefinitionReader加载bean properties文件，然后将解析产生的BeanDefinition注册到容器容器中
		annotatedBeanDefinitionReader.register(Service1.class, Service2.class);

		// 使BeanPostProcessor生效
		factory.getBeansOfType(BeanPostProcessor.class).values().forEach(factory::addBeanPostProcessor); // @1
		//打印出注册的bean的配置信息
		for (String beanName : new String[]{"service1", "service2"}) {
			//通过名称从容器中获取对应的BeanDefinition信息
			BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
			//获取BeanDefinition具体使用的是哪个类
			String beanDefinitionClassName = beanDefinition.getClass().getName();
			//通过名称获取bean对象
			Object bean = factory.getBean(beanName);
			//打印输出
			System.out.println(beanName + ":");
			System.out.println("    beanDefinitionClassName：" + beanDefinitionClassName);
			System.out.println("    beanDefinition：" + beanDefinition);
			System.out.println("    bean：" + bean);
		}
	}
}
