package com.wrp.spring.lesson002.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author wrp
 * @since 2025-04-21 23:32
 **/
public class AwareBean implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {

	@Override
	public void setBeanName(String name) {
		System.out.println("setBeanName：" + name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory：" + beanFactory);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("setBeanClassLoader：" + classLoader);
	}

}