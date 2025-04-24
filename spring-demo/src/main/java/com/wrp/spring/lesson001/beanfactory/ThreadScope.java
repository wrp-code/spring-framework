package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月15日 10:51
 **/
public class ThreadScope implements Scope {

	public static final String THREAD_SCOPE = "thread";

	private final ThreadLocal<Map<String, Object>> threadScope = NamedThreadLocal.withInitial(
			"ThreadScope", HashMap::new);

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> scope = this.threadScope.get();
		Object scopedObject = scope.get(name);
		if (scopedObject == null) {
			scopedObject = objectFactory.getObject();
			scope.put(name, scopedObject);
		}
		return scopedObject;
	}

	@Override
	public Object remove(String name) {
		Map<String, Object> scope = this.threadScope.get();
		return scope.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		//bean作用域范围结束的时候调用的方法，用于bean清理
		System.out.println(name);
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
