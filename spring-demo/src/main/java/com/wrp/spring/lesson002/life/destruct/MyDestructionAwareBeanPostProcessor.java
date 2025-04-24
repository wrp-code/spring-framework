package com.wrp.spring.lesson002.life.destruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * @author wrp
 * @since 2025年04月23日 12:12
 **/
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {
	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
		System.out.println("准备销毁bean：" + beanName);
	}
}