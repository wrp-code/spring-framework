package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.demo3.User;
import org.junit.jupiter.api.Test;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

import java.lang.reflect.Constructor;

/**
 * @author wrp
 * @since 2025年05月20日 15:35
 **/
public class ObjenesisTest {

	@Test
	public void test1() {
		Objenesis objenesis = new ObjenesisStd();
		User user = objenesis.newInstance(User.class);
		System.out.println(user);
	}

	@Test
	public void test2() throws Exception {
		// 没有无参构造时，报错：java.lang.NoSuchMethodException: com.wrp.spring.framework.proxy.demo3.User.<init>()
		// 无参构造中主动报错，java.lang.RuntimeException: 不要通过反射创建User对象
		Constructor<User> constructor = User.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		User user = constructor.newInstance();
		System.out.println(user);
	}

	@Test
	public void test3() {
		// 复用Objenesis
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator<User> userObjectInstantiator = objenesis.getInstantiatorOf(User.class);
		User user1 = userObjectInstantiator.newInstance();
		System.out.println(user1);
		User user2 = userObjectInstantiator.newInstance();
		System.out.println(user2);
		System.out.println(user1 == user2);
	}
}
