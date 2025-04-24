package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年04月15日 14:06
 **/
public class NormalBean {
	public interface IService{} //@1
	public static class Bean1 implements DisposableBean,IService {

		public Bean1() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean2 implements DisposableBean,IService {

		public Bean2() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean3 implements DisposableBean {

		public Bean3() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}
}
