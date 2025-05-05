package com.wrp.spring.framework.beandefinition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author wrp
 * @since 2025年05月04日 14:08
 */
public class ImportXmlTest {

	@Test
	public void test() {
		String xml = "classpath:/framework/beandefinition/import-beans.xml";
		var beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		int beanCount = reader.loadBeanDefinitions(xml);
		Assertions.assertEquals(3, beanCount);
		Assertions.assertNotNull(beanFactory.getBean("user-import"));
		Assertions.assertNotNull(beanFactory.getBean("user-a"));
		Assertions.assertNotNull(beanFactory.getBean("user-b"));
		Assertions.assertThrowsExactly(NoSuchBeanDefinitionException.class,
				() -> beanFactory.getBean("user-c"));
	}
}
