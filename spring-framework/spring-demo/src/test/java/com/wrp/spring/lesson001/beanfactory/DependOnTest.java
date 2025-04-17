package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月15日 14:07
 **/
public class DependOnTest {

	/**
	 * 无依赖的bean创建和销毁的顺序
	 */
	@Test
	public void normalBean() {
		System.out.println("容器启动中!");
		String beanXml = "classpath:lesson001/normalBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println("容器启动完毕，准备关闭spring容器!");
		//关闭容器
		context.close();
		System.out.println("spring容器已关闭!");
	}

	/**
	 * 强依赖的bean的创建和销毁顺序测试
	 */
	@Test
	public void strongDependenceBean() {
		System.out.println("容器启动中!");
		String beanXml = "classpath:lesson001/strongDependenceBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println("容器启动完毕，准备关闭spring容器!");
		context.close();
		System.out.println("spring容器已关闭!");
	}

	/**
	 * 通过depend-on来干预bean创建和销毁顺序
	 */
	@Test
	public void dependOnBean() {
		System.out.println("容器启动中!");
		String beanXml = "classpath:lesson001/dependOnBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println("容器启动完毕，准备关闭spring容器!");
		context.close();
		System.out.println("spring容器已关闭!");
	}

	/**
	 * set注入，bean的创建和销毁的优先级
	 * 创建：xml的顺序
	 * 销毁：注入方先销毁、被注入方后销毁
	 */
	@Test
	public void dependOnSetterBean() {
		System.out.println("容器启动中!");
		String beanXml = "classpath:lesson001/diBySetterBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println("容器启动完毕，准备关闭spring容器!");
		context.close();
		System.out.println("spring容器已关闭!");
	}
}
