package com.wrp.spring.lesson002.multicontainer;

import com.wrp.spring.lesson002.multicontainer.module1.Module1Config;
import com.wrp.spring.lesson002.multicontainer.module2.Module2Config;
import com.wrp.spring.lesson002.multicontainer.module2.Service3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author wrp
 * @since 2025年04月23日 15:15
 **/
public class ParentFactoryTest {
	// 因为BeanName相同而导致报错
	@Test
	public void test1() {
		//定义容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//注册bean
		context.register(Module1Config.class, Module2Config.class); //@1
		//启动容器
		context.refresh();
	}

	@Test
	public void test2() {
		//创建父容器
		AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
		//向父容器中注册Module1Config配置类
		parentContext.register(Module1Config.class);
		//启动父容器
		parentContext.refresh();

		//创建子容器
		AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
		//向子容器中注册Module2Config配置类
		childContext.register(Module2Config.class);
		//给子容器设置父容器
		childContext.setParent(parentContext);
		//启动子容器
		childContext.refresh();

		//从子容器中获取Service3
		Service3 service3 = childContext.getBean(Service3.class);
		System.out.println(service3.m1());
		System.out.println(service3.m2());
	}

	@Test
	public void test3() {
		//创建父容器parentFactory
		DefaultListableBeanFactory parentFactory = new DefaultListableBeanFactory();
		//向父容器parentFactory注册一个bean[userName->"路人甲Java"]
		parentFactory.registerBeanDefinition("userName",
				BeanDefinitionBuilder.
						genericBeanDefinition(String.class).
						addConstructorArgValue("路人甲Java").
						getBeanDefinition());

		//创建一个子容器childFactory
		DefaultListableBeanFactory childFactory = new DefaultListableBeanFactory();
		//调用setParentBeanFactory指定父容器
		childFactory.setParentBeanFactory(parentFactory);
		//向子容器parentFactory注册一个bean[address->"上海"]
		childFactory.registerBeanDefinition("address",
				BeanDefinitionBuilder.
						genericBeanDefinition(String.class).
						addConstructorArgValue("上海").
						getBeanDefinition());

		System.out.println("获取bean【userName】：" + childFactory.getBean("userName"));//@1

		System.out.println(Arrays.asList(childFactory.getBeanNamesForType(String.class))); //@2

		System.out.println(Arrays.asList(BeanFactoryUtils.beanNamesIncludingAncestors(childFactory)));
	}
}
