package com.wrp.spring.lesson002.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author wrp
 * @since 2025-04-21 22:45
 **/
public class MySmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

	@Override
	public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println(beanClass);
		System.out.println("调用 MySmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors 方法");
		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
		if (declaredConstructors != null) {
			//获取有@MyAutowried注解的构造器列表
			List<Constructor<?>> collect = Arrays.stream(declaredConstructors).
					filter(constructor -> constructor.isAnnotationPresent(MyAutowired.class)).
					collect(Collectors.toList());
			Constructor[] constructors = collect.toArray(new Constructor[collect.size()]);
			return constructors.length != 0 ? constructors : null;
		} else {
			return null;
		}
	}
}
