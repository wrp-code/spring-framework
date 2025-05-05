package com.wrp.spring.framework.dependon;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:31
 **/
@Component
@Accessors(chain = true)
@Data
public class Service1 implements DisposableBean {
	Service2 service2;

	public Service1() {
		System.out.println("create Service11");
	}

	public Service1(Service2 service2) {
		System.out.println("create Service1");
		this.service2 = service2;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destroy Service1");
	}
}