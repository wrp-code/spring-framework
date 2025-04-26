package com.wrp.spring.framework.beandefinition;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wrp
 * @since 2025-04-26 21:06
 **/
public class MyThreadScope implements Scope {

	public static final String THREAD_SCOPE = "thread";

	ThreadLocal<Map<String, Object>> threadLocal =
			ThreadLocal.withInitial(ConcurrentHashMap::new);

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> map = threadLocal.get();
		Object bean = map.get(name);
		if(bean == null) {
			bean = objectFactory.getObject();
			map.putIfAbsent(name, bean);
		}
		return bean;
	}

	@Override
	public Object remove(String name) {
		return threadLocal.get().remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		System.out.println(name + ": 被移除了");
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return Thread.currentThread().getName();
	}
}
