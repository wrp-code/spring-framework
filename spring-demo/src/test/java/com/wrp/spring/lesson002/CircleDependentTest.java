package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.circlerefer.singleandprototype.construction.Config4;
import com.wrp.spring.lesson002.circlerefer.singleandprototype.construction.ServiceC;
import com.wrp.spring.lesson002.circlerefer.singleandprototype.construction.ServiceD;
import com.wrp.spring.lesson002.circlerefer.singleandprototype.set.Config3;
import com.wrp.spring.lesson002.circlerefer.singleandprototype.set.ServiceF;
import com.wrp.spring.lesson002.circlerefer.twoprototype.construction.Config2;
import com.wrp.spring.lesson002.circlerefer.twoprototype.set.Config1;
import com.wrp.spring.lesson002.circlerefer.twoprototype.set.ServiceA;
import com.wrp.spring.lesson002.circlerefer.why.MainConfig3;
import com.wrp.spring.lesson002.circlerefer.why.MainConfig4;
import com.wrp.spring.lesson002.circlerefer.why.Service1;
import com.wrp.spring.lesson002.circlerefer.why.Service2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025-04-24 07:40
 **/
public class CircleDependentTest {

	// BeanCurrentlyInCreationException
	@Test
	public void test1() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(Config1.class);
		ServiceA bean = applicationContext.getBean(ServiceA.class);
		System.out.println(bean);
	}

	// BeanCurrentlyInCreationException
	@Test
	public void test2() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(Config2.class);
		com.wrp.spring.lesson002.circlerefer.twoprototype.construction.ServiceA bean =
				applicationContext.getBean(com.wrp.spring.lesson002.circlerefer.twoprototype.construction.ServiceA.class);
		System.out.println(bean);
	}

	// BeanCurrentlyInCreationException
	@Test
	public void test3() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(Config4.class);
		ServiceC bean = applicationContext.getBean(ServiceC.class);
		System.out.println(bean);
	}

	// BeanCurrentlyInCreationException
	@Test
	public void test4() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(Config4.class);
		ServiceD bean = applicationContext.getBean(ServiceD.class);
		System.out.println(bean);
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(Config3.class);
		ServiceF bean = applicationContext.getBean(ServiceF.class);
		System.out.println(bean);
	}


	@Test
	public void test30() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig3.class);
		context.refresh();
		Service2 bean = context.getBean(Service2.class);
		bean.m1();
	}
	@Test
	public void test31() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//创建一个BeanFactoryPostProcessor：BeanFactory后置处理器
		context.addBeanFactoryPostProcessor(beanFactory -> {
			if (beanFactory instanceof DefaultListableBeanFactory) {
				//将allowRawInjectionDespiteWrapping设置为true,允许早期注入的Bean和最终Spring中的Bean不一致。
				((DefaultListableBeanFactory) beanFactory).setAllowRawInjectionDespiteWrapping(true);
			}
		});
		context.register(MainConfig3.class);
		context.refresh();

		System.out.println("容器初始化完毕");

		//获取service1
		Service1 service1 = context.getBean(Service1.class);
		//获取service2
		Service2 service2 = context.getBean(Service2.class);

		System.out.println("----A-----");
		service2.m1(); //@1
		System.out.println("----B-----");
		service1.m1(); //@2
		System.out.println("----C-----");
		System.out.println(service2.getService1() == service1);
	}

	@Test
	public void test32() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
		System.out.println("容器初始化完毕");

		//获取service1
		Service1  service1 = context.getBean(Service1.class);
		//获取service2
		Service2 service2 = context.getBean(Service2.class);

		System.out.println("----A-----");
		service2.m1(); //@1
		System.out.println("----B-----");
		service1.m1(); //@2
		System.out.println("----C-----");
		System.out.println(service2.getService1() == service1);
	}
}
