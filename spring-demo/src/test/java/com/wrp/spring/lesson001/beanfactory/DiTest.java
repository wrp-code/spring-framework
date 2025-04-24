package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月15日 11:59
 **/
public class DiTest {

	/**
	 * 通过构造器的参数位置注入
	 */
	@Test
	public void diByConstructorParamIndex() {
		String beanXml = "classpath:lesson001/diByConstructorParamIndex.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diByConstructorParamIndex"));
	}

	/**
	 * 通过构造器的参数类型注入
	 */
	@Test
	public void diByConstructorParamType() {
		String beanXml = "classpath:lesson001/diByConstructorParamType.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diByConstructorParamType"));
	}

	/**
	 * 通过构造器的参数名称注入
	 */
	@Test
	public void diByConstructorParamName() {
		String beanXml = "classpath:lesson001/diByConstructorParamName.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diByConstructorParamName"));
	}

	/**
	 * 通过setter方法注入
	 */
	@Test
	public void diBySetter() {
		String beanXml = "classpath:lesson001/diBySetter.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diBySetter"));
	}

	@Test
	public void diBean(){
		String beanXml = "classpath:lesson001/diBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diBeanByConstructor"));
		System.out.println(context.getBean("diBeanBySetter"));
	}

	// 内部Bean不会被注册到Spring容器中
	@Test
	public void diBean2(){
		String beanXml = "classpath:lesson001/diBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		Map<String, CarModel> beansOfType = beanFactory.getBeansOfType(CarModel.class);
		beansOfType.forEach((k,v)-> System.out.println(k + " : " + v));
		Assertions.assertEquals(beansOfType.size(), 0 );
	}

	/**
	 * 其他各种类型注入
	 */
	@Test
	public void diOtherType() {
		String beanXml = "classpath:lesson001/diOtherType.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		System.out.println(context.getBean("diOtherType"));
	}
}
