package com.wrp.spring.framework.importclass.demo6;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author wrp
 * @since 2025-05-19 07:51
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
