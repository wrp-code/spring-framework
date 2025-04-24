package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月15日 14:48
 **/
public class PrimaryTest {

	// NoUniqueBeanDefinitionException 通过类型获取Bean，容器中出现多个
	@Test
	public void normalBean() {
		String beanXml = "classpath:lesson001/normalBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		//下面我们通过spring容器的T getBean(Class<T> requiredType)方法获取容器中对应的bean

		NormalBean.IService service = context.getBean(NormalBean.IService.class); //@1
		System.out.println(service);
	}

	// NoUniqueBeanDefinitionException set注入，被注入的类型有多个Bean
	@Test
	public void setterBean() {
		String beanXml = "classpath:lesson001/setterBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
	}

	@Test
	public void primaryBean() {
		String beanXml = "classpath:lesson001/primaryBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		PrimaryBean.IService serviceA = context.getBean("serviceA", PrimaryBean.IService.class); //@1
		Assertions.assertNotNull(serviceA);
		PrimaryBean.IService serviceB = context.getBean("serviceB", PrimaryBean.IService.class); //@1
		Assertions.assertNotNull(serviceB);
		PrimaryBean primaryBean = context.getBean(PrimaryBean.class); //@2
		Assertions.assertEquals(primaryBean.services.size(), 2);
	}

	@Test
	public void noPrimaryBean() {
		String beanXml = "classpath:lesson001/primaryBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		PrimaryBean.IService service = context.getBean("serviceB", PrimaryBean.IService.class); //@1
		Assertions.assertNotNull(service);
	}
}
