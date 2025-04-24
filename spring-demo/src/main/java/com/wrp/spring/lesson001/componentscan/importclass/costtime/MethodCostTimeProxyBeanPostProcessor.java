package com.wrp.spring.lesson001.componentscan.importclass.costtime;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author wrp
 * @since 2025年04月21日 14:34
 **/
public class MethodCostTimeProxyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().getName().toLowerCase().contains("service")) {
			return CostTimeProxy.createProxy(bean); //@1
		} else {
			return bean;
		}
	}
}