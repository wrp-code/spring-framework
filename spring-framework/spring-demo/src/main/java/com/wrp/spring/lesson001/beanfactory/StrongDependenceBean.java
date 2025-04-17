package com.wrp.spring.lesson001.beanfactory;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author wrp
 * @since 2025年04月15日 14:10
 **/
public class StrongDependenceBean {
	public static class Bean1 implements DisposableBean {

		public Bean1() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean2 implements DisposableBean {

		private Bean1 bean1;

		public Bean2(Bean1 bean1) { //@1
			this.bean1 = bean1;
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean3 implements DisposableBean {

		private Bean2 bean2;

		public Bean3(Bean2 bean2) { //@2
			this.bean2 = bean2;
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}
}
