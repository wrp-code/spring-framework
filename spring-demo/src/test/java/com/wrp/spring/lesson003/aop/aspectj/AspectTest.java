package com.wrp.spring.lesson003.aop.aspectj;

import com.wrp.spring.lesson003.aop.Service1;
import com.wrp.spring.lesson003.aop.aspect.Aspect1;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author wrp
 * @since 2025-04-25 21:17
 **/
public class AspectTest {

	@Test
	public void test1() {
		try {
			//对应目标对象
			Service1 target = new Service1();
			//创建AspectJProxyFactory对象
			AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
			//设置被代理的目标对象
			proxyFactory.setTarget(target);
			//设置标注了@Aspect注解的类
			proxyFactory.addAspect(Aspect1.class);
			//生成代理对象
			Service1 proxy = proxyFactory.getProxy();
			//使用代理对象
			proxy.m1();
			proxy.m2();
		} catch (Exception e) {
		}
	}
}
