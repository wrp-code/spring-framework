package com.wrp.spring.framework.proxy;

import com.wrp.spring.framework.proxy.demo.*;
import com.wrp.spring.framework.proxy.demo2.DefaultMethodInfo;
import com.wrp.spring.framework.proxy.demo2.IMethodInfo;
import com.wrp.spring.framework.proxy.demo2.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wrp
 * @since 2025-05-20 07:45
 **/
public class ProxyTest {

	@Test
	public void test1() {
		ServiceImpl service = new ServiceImpl();
		CostTimeProxy proxy = new CostTimeProxy(service);
		proxy.m1();
		proxy.m2();
		proxy.m3();
	}

	@Test
	public void test2() {
		ServiceImpl service = new ServiceImpl();
		// 1. 创建代理类的处理器
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				long l = System.currentTimeMillis();
				Object res = method.invoke(service, args);
				System.out.println((System.currentTimeMillis() - l) + " 毫秒");
				return res;
			}
		};
		// 2. 创建代理实例
		IService proxyService = (IService) Proxy.newProxyInstance(
				IService.class.getClassLoader(),
				new Class[]{IService.class},
				invocationHandler);
		// 3. 调用代理的方法
		proxyService.m1();
		proxyService.m2();
		proxyService.m3();
	}

	@Test
	public void test3() {
		// 不生效，需要main方法执行
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
		ServiceImpl service = new ServiceImpl();
		IService proxy = CostTimeInvocationHandler.createProxy(service);
		proxy.m1();
		proxy.m2();
		proxy.m3();
	}

	@Test
	public void test4() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				long l = System.currentTimeMillis();
				Object res = proxy.invokeSuper(obj, args);
				System.out.println((System.currentTimeMillis() - l) + " 毫秒");
				return res;
			}
		});

		ServiceImpl proxy = (ServiceImpl) enhancer.create();
		proxy.m1();
	}

	@Test
	public void test5() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(DefaultService.class);
		enhancer.setCallback(new FixedValue() {
			@Override
			public Object loadObject() throws Exception {
				return "wrp ";
			}
		});
		DefaultService proxy = (DefaultService) enhancer.create();
		System.out.println(proxy.m1());
		System.out.println(proxy.m2());
	}

	@Test
	public void test6() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(DefaultService.class);
		enhancer.setCallback(NoOp.INSTANCE);
		DefaultService proxy = (DefaultService) enhancer.create();
		System.out.println(proxy.m1());
	}

	@Test
	public void test7() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(DefaultService.class);
		Callback[] callbacks = {
				new MethodInterceptor() {
					@Override
					public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
						long starTime = System.nanoTime();
						Object result = methodProxy.invokeSuper(o, objects);
						long endTime = System.nanoTime();
						System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
						return result;
					}
				},
				//下面这个用来拦截所有get开头的方法，返回固定值的
				new FixedValue() {
					@Override
					public Object loadObject() throws Exception {
						return "wrp";
					}
				}
		};
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				return method.getName().equals("m1") ? 0 : 1;
			}
		});
		DefaultService proxy = (DefaultService) enhancer.create();
		System.out.println(proxy.m1());
		System.out.println(proxy.m2());
	}

	@Test
	public void test8() {
		DefaultService proxy = CostTimeEnhancer.create(DefaultService.class);
		System.out.println(proxy.m1());
	}

	@Test
	public void test9() {
		ServiceImpl service = new ServiceImpl();
		// 1. 创建代理类的处理器
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				long l = System.currentTimeMillis();
				Object res = method.invoke(service, args);
				System.out.println((System.currentTimeMillis() - l) + " 毫秒");
				return res;
			}
		};
		// 2. 创建代理实例
		Object proxy = Proxy.newProxyInstance(
				IService.class.getClassLoader(),
				new Class[]{IService.class},
				invocationHandler);
		System.out.println(proxy.getClass());
	}

	@Test
	public void test10() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setInterfaces(new Class[]{IService.class, ServiceB.class});
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println(method.getName());
				return null;
			}
		});
		Object proxy = enhancer.create();
		if(proxy instanceof ServiceB serviceB) {
			serviceB.doSomething();
		}
		if(proxy instanceof IService iService) {
			iService.m1();
		}

		System.out.println(proxy.getClass());
		System.out.println("parent: " + proxy.getClass().getSuperclass());
		for (Class<?> cs : proxy.getClass().getInterfaces()) {
			System.out.println(cs);
		}
	}

	@Test
	public void test11() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setCallback(new LazyLoader() {
			@Override
			public Object loadObject() throws Exception {
				System.out.println("调用LazyLoader.loadObject()方法");
				return new ServiceImpl();
			}
		});
		ServiceImpl service = (ServiceImpl) enhancer.create();
		System.out.println("第一次调用");
		service.m1();
		System.out.println("第二次调用");
		service.m1();
	}

	@Test
	public void test12() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setCallback(new Dispatcher() {
			@Override
			public Object loadObject() throws Exception {
				System.out.println("Dispatcher.loadObject()方法");
				return new ServiceImpl();
			}
		});
		ServiceImpl service = (ServiceImpl) enhancer.create();
		System.out.println("第一次调用");
		service.m1();
		System.out.println("第二次调用");
		service.m1();
	}

	@Test
	public void test13() {
		Class<?> targetClass = UserService.class;
		Enhancer enhancer = new Enhancer();
		//设置代理的父类
		enhancer.setSuperclass(targetClass);
		//设置代理需要实现的接口列表
		enhancer.setInterfaces(new Class[]{IMethodInfo.class});
		//创建一个方法统计器
		IMethodInfo methodInfo = new DefaultMethodInfo(targetClass);
		//创建会调用器列表，此处定义了2个，第1个用于处理UserService中所有的方法，第2个用来处理IMethodInfo接口中的方法
		Callback[] callbacks = {
				new MethodInterceptor() {
					@Override
					public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
						return methodProxy.invokeSuper(o, objects);
					}
				},
				new Dispatcher() {
					@Override
					public Object loadObject() throws Exception {
						/**
						 * 用来处理代理对象中IMethodInfo接口中的所有方法
						 * 所以此处返回的为IMethodInfo类型的对象，
						 * 将由这个对象来处理代理对象中IMethodInfo接口中的所有方法
						 */
						return methodInfo;
					}
				}
		};
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				//当方法在IMethodInfo中定义的时候，返回callbacks中的第二个元素
				return method.getDeclaringClass() == IMethodInfo.class ? 1 : 0;
			}
		});

		Object proxy = enhancer.create();
		//代理的父类是UserService
		UserService userService = (UserService) proxy;
		userService.add();
		//代理实现了IMethodInfo接口
		IMethodInfo mf = (IMethodInfo) proxy;
		System.out.println(mf.methodCount());
		System.out.println(mf.methodNames());
	}

	@Test
	public void test14() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ServiceImpl.class);
		enhancer.setCallback(NoOp.INSTANCE);
		//通过Enhancer.setNamingPolicy来设置代理类的命名策略
		enhancer.setNamingPolicy(new DefaultNamingPolicy() {
			@Override
			protected String getTag() {
				return "-test-";
			}
		});
		Object proxy = enhancer.create();
		System.out.println(proxy.getClass());
	}

}
