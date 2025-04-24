package com.wrp.spring.lesson002.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wrp
 * @since 2025-04-23 21:53
 **/
public class SimpleEventMulticaster implements EventMulticaster {

	private Map<Class<?>, List<EventListener>> eventObjectEventListenerMap = new ConcurrentHashMap<>();

	@Override
	public void multicastEvent(AbstractEvent event) {
		List<EventListener> eventListeners = this.eventObjectEventListenerMap.get(event.getClass());
		if (eventListeners != null) {
			for (EventListener eventListener : eventListeners) {
				eventListener.onEvent(event);
			}
		}
	}

	@Override
	public void addEventListener(EventListener<?> listener) {
		Class<?> eventType = this.getEventType(listener);
		List<EventListener> eventListeners = this.eventObjectEventListenerMap.get(eventType);
		if (eventListeners == null) {
			eventListeners = new ArrayList<>();
			this.eventObjectEventListenerMap.put(eventType, eventListeners);
		}
		eventListeners.add(listener);
	}

	@Override
	public void removeEventListener(EventListener<?> listener) {
		Class<?> eventType = this.getEventType(listener);
		List<EventListener> eventListeners = this.eventObjectEventListenerMap.get(eventType);
		if (eventListeners != null) {
			eventListeners.remove(listener);
		}
	}

	/**
	 * 获取事件监听器需要监听的事件类型
	 *
	 * @param listener
	 * @return
	 */
	protected Class<?> getEventType(EventListener listener) {
		ParameterizedType parameterizedType = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
		Type eventType = parameterizedType.getActualTypeArguments()[0];
		return (Class<?>) eventType;
	}

}