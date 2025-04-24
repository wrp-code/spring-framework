package com.wrp.spring.lesson002.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author wrp
 * @since 2025-04-21 22:55
 **/
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println("调用postProcessBeforeInstantiation()");
		//发现类型是Car类型的时候，硬编码创建一个Car对象返回
		if (beanClass == Car.class) {
			Car car = new Car();
			car.setName("保时捷");
			return car;
		}
		return null;
	}
}
