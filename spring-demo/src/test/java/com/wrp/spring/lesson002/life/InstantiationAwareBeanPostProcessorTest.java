package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 *
 * @author wrp
 * @since 2025-04-21 22:35
 **/
public class InstantiationAwareBeanPostProcessorTest {

	// 阶段6.1，实例化前操作，{@link InstantiationAwareBeanPostProcessor}可以返回指定bean，从而跳过Spring的初始化
	@Test
	public void test1() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//添加一个BeanPostProcessor：InstantiationAwareBeanPostProcessor
		factory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

		//定义一个car bean,车名为：奥迪
		AbstractBeanDefinition carBeanDefinition = BeanDefinitionBuilder.
				genericBeanDefinition(Car.class).
				addPropertyValue("name", "奥迪").  //@2
						getBeanDefinition();
		factory.registerBeanDefinition("car", carBeanDefinition);
		//从容器中获取car这个bean的实例，输出
		System.out.println(factory.getBean("car"));

	}

	// 阶段8.2：实例化后处理
	@Test
	public void test2() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
			@Override
			public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
				if ("user1".equals(beanName)) {
					return false;
				} else {
					return true;
				}
			}
		});

		factory.registerBeanDefinition("user1", BeanDefinitionBuilder.
				genericBeanDefinition(UserModel.class).
				addPropertyValue("name", "路人甲Java").
				addPropertyValue("age", 30).
				getBeanDefinition());

		factory.registerBeanDefinition("user2", BeanDefinitionBuilder.
				genericBeanDefinition(UserModel.class).
				addPropertyValue("name", "刘德华").
				addPropertyValue("age", 50).
				getBeanDefinition());

		for (String beanName : factory.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
		}
	}

	// 阶段8.3：Bean属性赋值前阶段
	@Test
	public void test3() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() { // @0
			@Override
			public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
				if ("user1".equals(beanName)) {
					if (pvs == null) {
						pvs = new MutablePropertyValues();
					}
					if (pvs instanceof MutablePropertyValues) {
						MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
						//将姓名设置为：路人
						mpvs.add("name", "路人");
						//将年龄属性的值修改为18
						mpvs.add("age", 18);
					}
				}
				return pvs;
			}
		});

		//注意 user1 这个没有给属性设置值
		factory.registerBeanDefinition("user1", BeanDefinitionBuilder.
				genericBeanDefinition(UserModel.class).
				getBeanDefinition()); //@1

		factory.registerBeanDefinition("user2", BeanDefinitionBuilder.
				genericBeanDefinition(UserModel.class).
				addPropertyValue("name", "刘德华").
				addPropertyValue("age", 50).
				getBeanDefinition());

		for (String beanName : factory.getBeanDefinitionNames()) {
			System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
		}
	}
}
