package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.List;

/**
 * @author wrp
 * @since 2025年04月15日 13:37
 **/
public class AutoDiTest {
	// c1.isAssignableFrom(c2) c2是不是c1的子类
	@Test
	public void isAssignableFrom(){
		System.out.println(Object.class.isAssignableFrom(Integer.class)); //true
		System.out.println(Object.class.isAssignableFrom(int.class)); //false
		System.out.println(Object.class.isAssignableFrom(List.class)); //true
		System.out.println(Collection.class.isAssignableFrom(List.class)); //true
		System.out.println(List.class.isAssignableFrom(Collection.class)); //false
	}

	/**
	 * 按照名称进行注入
	 */
	@Test
	public void diAutowireByName() {
		String beanXml = "classpath:lesson001/diAutowireByName.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diAutowireByName1"));
		System.out.println(context.getBean("diAutowireByName2"));
	}

	/**
	 * 按照set方法参数类型进行注入
	 */
	@Test
	public void diAutowireByType() {
		String beanXml = "classpath:lesson001/diAutowireByType.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diAutowireByType1"));
	}

	/**
	 * 按照类型注入集合
	 */
	@Test
	public void diAutowireByTypeExtend() {
		String beanXml = "classpath:lesson001/diAutowireByTypeExtend.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		//从容器中获取DiAutowireByTypeExtend
		DiAutowireByTypeExtend diAutowireByTypeExtend = context.getBean(DiAutowireByTypeExtend.class);
		//输出diAutowireByTypeExtend中的属性看一下
		System.out.println("serviceList：" + diAutowireByTypeExtend.getServiceList());
		System.out.println("baseServieList：" + diAutowireByTypeExtend.getBaseServieList());
		System.out.println("service1Map：" + diAutowireByTypeExtend.getService1Map());
		System.out.println("baseServieMap：" + diAutowireByTypeExtend.getBaseServieMap());
	}

	/**
	 * 构造函数的方式进行自动注入
	 *
	 * 贪婪的方式，尽可能多的注入Bean
	 */
	@Test
	public void diAutowireByConstructor() {
		String beanXml = "classpath:lesson001/diAutowireByConstructor.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diAutowireByConstructor"));
	}

	/**
	 * autowire=default
	 */
	@Test
	public void diAutowireByDefault() {
		String beanXml = "classpath:lesson001/diAutowireByDefault.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diAutowireByDefault1"));
		System.out.println(context.getBean("diAutowireByDefault2"));
	}
}
