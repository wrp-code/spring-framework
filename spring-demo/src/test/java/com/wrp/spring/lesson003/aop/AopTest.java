package com.wrp.spring.lesson003.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author wrp
 * @since 2025-04-24 23:37
 **/
public class AopTest {

	@Test
	public void test1() {
		//定义目标对象
		UserService target = new UserService();
		//创建pointcut，用来拦截UserService中的work方法
		Pointcut pointcut = new Pointcut() {
			@Override
			public ClassFilter getClassFilter() {
				//判断是否是UserService类型的
				return clazz -> UserService.class.isAssignableFrom(clazz);
			}

			@Override
			public MethodMatcher getMethodMatcher() {
				return new MethodMatcher() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						//判断方法名称是否是work
						return "work".equals(method.getName());
					}

					@Override
					public boolean isRuntime() {
						return false;
					}

					@Override
					public boolean matches(Method method, Class<?> targetClass, Object... args) {
						return false;
					}
				};
			}
		};
		//创建通知，此处需要在方法之前执行操作，所以需要用到MethodBeforeAdvice类型的通知
		MethodBeforeAdvice advice = (method, args, target1) -> System.out.println("你好:" + args[0]);

		//创建Advisor，将pointcut和advice组装起来
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

		//通过spring提供的代理创建工厂来创建代理
		ProxyFactory proxyFactory = new ProxyFactory();
		//为工厂指定目标对象
		proxyFactory.setTarget(target);
		//调用addAdvisor方法，为目标添加增强的功能，即添加Advisor，可以为目标添加很多个Advisor
		proxyFactory.addAdvisor(advisor);
		//通过工厂提供的方法来生成代理对象
		UserService userServiceProxy = (UserService) proxyFactory.getProxy();

		//调用代理的work方法
		userServiceProxy.work("路人");
	}

	@Test
	public void test2() {
		//定义目标对象
		UserService target = new UserService();
		//创建pointcut，用来拦截UserService中的work方法
		Pointcut pointcut = new Pointcut() {
			@Override
			public ClassFilter getClassFilter() {
				//判断是否是UserService类型的
				return clazz -> UserService.class.isAssignableFrom(clazz);
			}

			@Override
			public MethodMatcher getMethodMatcher() {
				return new MethodMatcher() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						//判断方法名称是否是work
						return "work".equals(method.getName());
					}

					@Override
					public boolean isRuntime() {
						return true; // @1：注意这个地方要返回true
					}

					@Override
					public boolean matches(Method method, Class<?> targetClass, Object... args) {
						// @2：isRuntime为true的时候，会执行这个方法
						if (Objects.nonNull(args) && args.length == 1) {
							String userName = (String) args[0];
							return userName.contains("粉丝");
						}
						return false;
					}
				};
			}
		};
		//创建通知，此处需要在方法之前执行操作，所以需要用到MethodBeforeAdvice类型的通知
		MethodBeforeAdvice advice = (method, args, target1) -> System.out.println("感谢您一路的支持!");

		AfterReturningAdvice afterReturningAdvice = (returnValue, method, args, target1) -> System.out.println("后置处理!");

		//创建Advisor，将pointcut和advice组装起来
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
		DefaultPointcutAdvisor afterAdvisor = new DefaultPointcutAdvisor(pointcut, afterReturningAdvice);

		//通过spring提供的代理创建工厂来创建代理
		ProxyFactory proxyFactory = new ProxyFactory();
		//为工厂指定目标对象
		proxyFactory.setTarget(target);
		//调用addAdvisor方法，为目标添加增强的功能，即添加Advisor，可以为目标添加很多个Advisor
		proxyFactory.addAdvisor(advisor);
		proxyFactory.addAdvisor(afterAdvisor);
		//通过工厂提供的方法来生成代理对象
		UserService userServiceProxy = (UserService) proxyFactory.getProxy();

		//调用代理的work方法
		userServiceProxy.work("粉丝：A");
	}

	@Test
	public void test4() {
		FundsService fundsService = new FundsService();
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(fundsService);
		proxyFactory.addAdvice(new MethodBeforeAdvice(){
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				String userName = (String) args[0];
				if(!Objects.equals("wrp", userName)) {
					throw new RuntimeException("非法访问");
				}
			}
		});

		FundsService proxy = (FundsService) proxyFactory.getProxy();
		proxy.recharge("wrp", 100);
		proxy.recharge("Andy", 100);
	}

	@Test
	public void test5() {
		//代理工厂
		ProxyFactory proxyFactory = new ProxyFactory(new FundsService());
		//添加一个异常通知，发现异常之后发送消息给开发者尽快修复bug
		proxyFactory.addAdvice(new SendMsgThrowsAdvice());
		//通过代理工厂创建代理
		FundsService proxy = (FundsService) proxyFactory.getProxy();
		//调用代理的方法
		proxy.cashOut("路人", 2000);
	}

	@Test
	public void test6() {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new FundsService());
		proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println(method);
			}
		}));
		//创建代理对象
		Object proxy = proxyFactory.getProxy();
		System.out.println("代理对象的类型：" + proxy.getClass());
		System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
		System.out.println("代理对象实现的接口列表");
		for (Class<?> cf : proxy.getClass().getInterfaces()) {
			System.out.println(cf);
		}
	}

	@Test
	public void test7() {
		Service target = new Service();

		ProxyFactory proxyFactory = new ProxyFactory();
		//设置需要被代理的对象
		proxyFactory.setTarget(target);
		//设置需要代理的接口
		proxyFactory.addInterface(IService.class);
		// 强制使用cglib
		proxyFactory.setProxyTargetClass(true);

		proxyFactory.addAdvice(new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println(method);
			}
		});

		IService proxy = (IService) proxyFactory.getProxy();
		System.out.println("代理对象的类型：" + proxy.getClass());
		System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
		System.out.println("代理对象实现的接口列表");
		for (Class<?> cf : proxy.getClass().getInterfaces()) {
			System.out.println(cf);
		}
		//调用代理的方法
		System.out.println("\n调用代理的方法");
		proxy.say("spring aop");
	}

	@Test
	public void test8() {
		Service2 target = new Service2();

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(target);
		proxyFactory.setExposeProxy(true);

		proxyFactory.addAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				long startTime = System.nanoTime();
				Object result = invocation.proceed();
				long endTime = System.nanoTime();
				System.out.println(String.format("%s方法耗时(纳秒):%s", invocation.getMethod().getName(), endTime - startTime));
				return result;
			}
		});

		Service2 proxy = (Service2) proxyFactory.getProxy();
		proxy.m1();
	}
}
