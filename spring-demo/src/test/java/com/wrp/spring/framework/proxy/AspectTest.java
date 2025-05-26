package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.aspectj.UserService;
import com.wrp.spring.framework.proxy.aspectj.execution.Aspect1;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author wrp
 * @since 2025-05-26 07:57
 **/
public class AspectTest {

	@Test
	public void test1() {
		UserService userService = new UserService();
		AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
		proxyFactory.setTarget(userService);
		proxyFactory.addAspect(Aspect1.class);
		UserService proxy = proxyFactory.getProxy();
		proxy.print("wrp");
	}
}
