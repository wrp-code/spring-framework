package com.wrp.spring.framework.dependon;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Component
@Accessors(chain = true)
@Data
public class Service2 implements DisposableBean {
	Service3 service3;

	public Service2() {
		System.out.println("create Service22");
	}

	public Service2(Service3 service3) {
		System.out.println("create Service2");
		this.service3 = service3;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destroy Service2");
	}
}