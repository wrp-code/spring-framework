package com.wrp.spring.lesson002.event;

/**
 * @author wrp
 * @since 2025-04-23 21:52
 **/
public interface EventListener<E extends AbstractEvent> {
	/**
	 * 此方法负责处理事件
	 *
	 * @param event 要响应的事件对象
	 */
	void onEvent(E event);
}