package com.wrp.spring.lesson002.event;

/**
 * @author wrp
 * @since 2025-04-23 21:51
 **/
public abstract class AbstractEvent {

	//事件源
	protected Object source;

	public AbstractEvent(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
}