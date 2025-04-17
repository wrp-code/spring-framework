package com.wrp.spring.lesson001.beanfactory;

/**
 * @author wrp
 * @since 2025年04月16日 10:31
 **/
public class ActualTimeDependencyLazyBean {

	public ActualTimeDependencyLazyBean() {
		System.out.println("ActualTimeDependencyLazyBean实例化!");
	}

	private LazyInitBean lazyInitBean;

	public LazyInitBean getLazyInitBean() {
		return lazyInitBean;
	}

	public void setLazyInitBean(LazyInitBean lazyInitBean) {
		this.lazyInitBean = lazyInitBean;
		System.out.println("ActualTimeDependencyLazyBean.setLazyInitBean方法!");
	}
}