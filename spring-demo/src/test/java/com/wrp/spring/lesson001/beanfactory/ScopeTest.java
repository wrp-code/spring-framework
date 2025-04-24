package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025年04月15日 10:44
 **/
public class ScopeTest {
	ClassPathXmlApplicationContext context;

	@BeforeEach
	public void before() {
		System.out.println("spring容器准备启动.....");
		//1.bean配置文件位置
		String beanXml = "classpath:lesson001/scope-beans.xml";
		//2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
		this.context = new ClassPathXmlApplicationContext(beanXml);
		System.out.println("spring容器启动完毕！");
	}

	/**
	 * 单例bean
	 */
	@Test
	public void singletonBean() {
		System.out.println("---------单例bean，每次获取的bean实例都一样---------");
		System.out.println(context.getBean("singletonBean"));
		System.out.println(context.getBean("singletonBean"));
		System.out.println(context.getBean("singletonBean"));
	}

	/**
	 * 多例bean
	 */
	@Test
	public void prototypeBean() {
		System.out.println("---------多例bean，每次获取的bean实例都不一样---------");
		System.out.println(context.getBean("prototypeBean"));
		System.out.println(context.getBean("prototypeBean"));
		System.out.println(context.getBean("prototypeBean"));
	}

	@Test
	public void threadScope() throws InterruptedException {
		String beanXml = "classpath:lesson001/thread-beans.xml";
		//手动创建容器
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml);
		//向容器中注册自定义的scope
		context.getBeanFactory().registerScope(ThreadScope.THREAD_SCOPE, new ThreadScope());//@1

		//使用容器获取bean
		for (int i = 0; i < 2; i++) { //@2
			new Thread(() -> {
				System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));
				System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));
			}).start();
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
