package com.wrp.spring.framework.autowirecandidate;

import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author wrp
 * @since 2025-05-07 22:08
 **/
@ToString
public class A implements BeanNameAware {

	String name;

	@Override
	public void setBeanName(String name) {
		this.name = name;
	}
}
