package com.wrp.spring.lesson003.aop.aspectj;

import com.wrp.spring.lesson003.aop.Service1;
import com.wrp.spring.lesson003.aop.aspect.AfterAspect4;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author wrp
 * @since 2025-04-25 21:49
 **/
public class AspectAdviceTest {

	@Test
	public void test4() {
		Service1 target = new Service1();
		Class<AfterAspect4> aspectClass = AfterAspect4.class;
		AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
		proxyFactory.setTarget(target);
		proxyFactory.addAspect(aspectClass);
		Service1 proxy = proxyFactory.getProxy();
		System.out.println(proxy.say("路人"));
		System.out.println(proxy.work("路人"));
	}
}
