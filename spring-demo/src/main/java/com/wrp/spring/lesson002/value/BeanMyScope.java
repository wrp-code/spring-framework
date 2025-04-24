package com.wrp.spring.lesson002.value;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * @author wrp
 * @since 2025年04月23日 15:43
 **/
public class BeanMyScope implements Scope {

	public static final String SCOPE_MY = "my"; //@1

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		System.out.println("BeanMyScope >>>>>>>>> get:" + name); //@2
		return objectFactory.getObject(); //@3
	}

	@Override
	public Object remove(String name) {
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {

	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}
}