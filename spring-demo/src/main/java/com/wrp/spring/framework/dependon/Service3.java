package com.wrp.spring.framework.dependon;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@Component
//@DependsOn({"service1", "service2"})
@Accessors(chain = true)
@Data
public class Service3 implements DisposableBean {

	public Service3() {
		System.out.println("create Service33");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destroy Service3");
	}
}