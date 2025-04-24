package com.wrp.spring.lesson002.life;

import com.wrp.spring.lesson002.life.destruct.Config;
import com.wrp.spring.lesson002.life.destruct.MyDestructionAwareBeanPostProcessor;
import com.wrp.spring.lesson002.life.destruct.ServiceA;
import com.wrp.spring.lesson002.life.destruct.ServiceB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * @author wrp
 * @since 2025年04月23日 12:12
 **/
public class DestructionAwareBeanPostProcessorTest {

	@Test
	public void test1() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//添加自定义的DestructionAwareBeanPostProcessor
		factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());

		//向容器中注入3个单例bean
		factory.registerBeanDefinition("serviceA1", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());
		factory.registerBeanDefinition("serviceA2", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());
		factory.registerBeanDefinition("serviceA3", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());

		//触发所有单例bean初始化
		factory.preInstantiateSingletons(); //@1

		System.out.println("销毁serviceA1");
		//销毁指定的bean
		factory.destroySingleton("serviceA1");//@2

		System.out.println("触发所有单例bean的销毁");
		factory.destroySingletons();
	}

	@Test
	public void test2() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//添加自定义的DestructionAwareBeanPostProcessor
		factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor()); //@1
		//将CommonAnnotationBeanPostProcessor加入
		factory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor()); //@2

		//向容器中注入bean
		factory.registerBeanDefinition("serviceB", BeanDefinitionBuilder.genericBeanDefinition(ServiceB.class).getBeanDefinition());

		//触发所有单例bean初始化
		factory.preInstantiateSingletons();

		System.out.println("销毁serviceB");
		//销毁指定的bean
		factory.destroySingleton("serviceB");
	}



	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(Config.class);
		//启动容器
		System.out.println("准备启动容器");
		context.refresh();
		System.out.println("容器启动完毕");
		System.out.println("serviceA：" + context.getBean(ServiceA.class));
		//关闭容器
		System.out.println("准备关闭容器");
		//调用容器的close方法，会触发bean的销毁操作
		context.close(); //@2
		System.out.println("容器关闭完毕");
	}
}
