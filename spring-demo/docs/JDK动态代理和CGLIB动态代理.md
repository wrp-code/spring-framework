## 1. 代理模式

```java
public interface IService {
    void m1();
    void m2();
    void m3();
}

public class ServiceImpl implements IService {
	@Override
	public void m1() {
		System.out.println("ServiceImpl m1...");
	}

	@Override
	public void m2() {
		System.out.println("ServiceImpl m2...");
	}

	@Override
	public void m3() {
		System.out.println("ServiceImpl m3...");
	}
}
```

实现调用方法的耗时统计

```java
public class CostTimeProxy implements IService {

    IService target;

    public CostTimeProxy(IService target) {
       this.target = target;
    }

    @Override
    public void m1() {
       long l = System.currentTimeMillis();
       target.m1();
       System.out.println((System.currentTimeMillis() - l) + "毫秒");
    }

    @Override
    public void m2() {
       long l = System.currentTimeMillis();
       target.m2();
       System.out.println((System.currentTimeMillis() - l) + "毫秒");
    }

    @Override
    public void m3() {
       long l = System.currentTimeMillis();
       target.m3();
       System.out.println((System.currentTimeMillis() - l) + "毫秒");
    }
}
```

```java
@Test
public void test1() {
    ServiceImpl service = new ServiceImpl();
    CostTimeProxy proxy = new CostTimeProxy(service);
    proxy.m1();
    proxy.m2();
    proxy.m3();
}
```

此种方式存在的问题：

- 需要为每个接口实现一个耗时统计的代理类。
- 每个方法都实现相同耗时统计功能，存在重复的代码。

## 2. JDK动态代理

> JDK代理的限制：**只能为接口创建代理类**，因为JDK代理类继承了`Proxy`类，而Java又是单继承的。

### 2.1 Proxy类常用方法

- `getProxyClass` 为指定的接口创建代理类，返回代理类的Class对象
- `newProxyInstance` 创建代理类的实例对象
- `isProxy` 判断指定的类是否是一个代理类
- `getInvocationHandler` 获取代理对象的`InvocationHandler`对象

### 2.2 InvocationHandler接口

```java
public interface InvocationHandler {
    // proxy 代理对象
    // method 调用方法
    // args 方法参数
    // invoke返回值 方法的返回值
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```

### 2.3 JDK代理实现耗时统计

> 通过这种方式，将耗时统计的功能代码封装起来，更好的重用性。

```java
public class CostTimeInvocationHandler implements InvocationHandler {
	private Object target;

	public CostTimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
		long l = System.currentTimeMillis();
		Object res = method.invoke(target, objects);
		System.out.println((System.currentTimeMillis() - l) + " 毫秒");
		return res;
	}

	public static <T> T createProxy(T target) {

		if(Arrays.isNullOrEmpty(target.getClass().getInterfaces())) {
			throw new IllegalArgumentException("jdk代理只能代理接口类");
		}
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new CostTimeInvocationHandler(target));
	}
}
```

```java
@Test
public void test3() {
    ServiceImpl service = new ServiceImpl();
    IService proxy = CostTimeInvocationHandler.createProxy(service);
    proxy.m1();
    proxy.m2();
    proxy.m3();
}
```

### 2.4 JDK代理原理

main方法内生效，test单元测试不生效。方法执行完成后，在根目录多一个jdk/proxy1目录

```java
public static void main(String[] args) {
    // JDK8
    System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    // 更高版本的JDK
    System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
    ServiceImpl service = new ServiceImpl();
    IService proxy = CostTimeInvocationHandler.createProxy(service);
    proxy.m1();
    proxy.m2();
    proxy.m3();
}
```

查看代理类.class文件

- 代理类继承Proxy，并实现接口
- 接口的方法都委派给了super.h变量，他是Proxy中的一个成员变量，本质就是我们定义的`InvocationHandler`

```java
public final class $Proxy0 extends Proxy implements IService {
    private static final Method m0;
    private static final Method m1;
    private static final Method m2;
    private static final Method m3;
    private static final Method m4;
    private static final Method m5;

    public $Proxy0(InvocationHandler var1) {
        super(var1);
    }

    public final int hashCode() {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean equals(Object var1) {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void m2() {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void m1() {
        try {
            super.h.invoke(this, m4, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void m3() {
        try {
            super.h.invoke(this, m5, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        ClassLoader var0 = $Proxy0.class.getClassLoader();

        try {
            m0 = Class.forName("java.lang.Object", false, var0).getMethod("hashCode");
            m1 = Class.forName("java.lang.Object", false, var0).getMethod("equals", Class.forName("java.lang.Object", false, var0));
            m2 = Class.forName("java.lang.Object", false, var0).getMethod("toString");
            m3 = Class.forName("com.wrp.spring.framework.proxy.demo.IService", false, var0).getMethod("m2");
            m4 = Class.forName("com.wrp.spring.framework.proxy.demo.IService", false, var0).getMethod("m1");
            m5 = Class.forName("com.wrp.spring.framework.proxy.demo.IService", false, var0).getMethod("m3");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }

    private static MethodHandles.Lookup proxyClassLookup(MethodHandles.Lookup var0) throws IllegalAccessException {
        if (var0.lookupClass() == Proxy.class && var0.hasFullPrivilegeAccess()) {
            return MethodHandles.lookup();
        } else {
            throw new IllegalAccessException(var0.toString());
        }
    }
}
```

### 2.5 如何区分JDK代理类

> 全限定类名带有$Proxy字样

## 3. CGLIB动态代理

> cglib是一个强大、高性能的字节码生成库，它用于在运行时扩展Java类和实现接口；本质上它是通过动态的生成一个子类去覆盖所要代理的类（非final修饰的类和方法）
>
> **CGLIB不能代理final类、final方法、static方法、private方法**

### 3.1 Enhancer类

- `setSuperclass` 设置目标类
- `setInterfaces` 设置被代理的接口
- `setCallback` 设置拦截器 
- `setCallbacks` 设置多个拦截器
- `setCallbackFilter` 设置拦截器过滤器，实现不同的方法使用不同的拦截器
- `create` 创建代理对象

### 3.2 常见的Callback

1. `MethodInterceptor`方法拦截器

```java
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
```

2. `FixedValue`返回固定值

```java
public class DefaultService {

	public String m1() {
		System.out.println("DefaultService m1...");
		return "m1";
	}

	public String m2() {
		System.out.println("DefaultService m2...");
		return "m2";
	}
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
```

3. `NoOp.INSTANCE`不做任何操作

```java
@Test
public void test6() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(DefaultService.class);
    enhancer.setCallback(NoOp.INSTANCE);
    DefaultService proxy = (DefaultService) enhancer.create();
    System.out.println(proxy.m1());
}
```

4. `LazyLoader`懒加载

> 当被增强bean的方法初次被调用时，会触发回调，之后每次再进行方法调用都是对LazyLoader第一次返回的bean调用

```java
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
```

```tex
第一次调用
调用LazyLoader.loadObject()方法
ServiceImpl m1...
第二次调用
ServiceImpl m1...
```

5. `Dispatcher`

> 每次都会调用`loadObject`方法，创建新的对象

```java
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
```

```tex
第一次调用
Dispatcher.loadObject()方法
ServiceImpl m1...
第二次调用
Dispatcher.loadObject()方法
ServiceImpl m1...
```

### 3.3 不同的方法使用不同的拦截器

> callbacks数组的索引

```java
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
```

### 3.4 CGLIB实现方法耗时统计

> 注意在`MethodInterceptor#intercept`方法中，需要调用`proxy.invokeSuper(obj, args)`
>
> 如果使用`method.invoke(obj, args)`，会发生内存溢出的错误，因为obj是代理对象，导致无限递归。

```java
public class CostTimeEnhancer {

	private static final CostTimeInterceptor COST_TIME_INTERCEPTOR = new CostTimeInterceptor();

	public static <T> T create(Class<T> target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target);
		enhancer.setCallback(COST_TIME_INTERCEPTOR);
		return (T) enhancer.create();
	}

	private static class CostTimeInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			long starTime = System.nanoTime();
			Object result = proxy.invokeSuper(obj, args);
			long endTime = System.nanoTime();
			System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
			return result;
		}
	}
}
```

```java
@Test
public void test8() {
    DefaultService proxy = CostTimeEnhancer.create(DefaultService.class);
    System.out.println(proxy.m1());
}
```

### 3.5 同时代理接口和类

> ServiceImpl实现了IService，但是ServiceB是独立的一个接口

```java
public interface ServiceB {
	void doSomething();
}

public interface IService {

	void m1();
	void m2();
	void m3();
}

public class ServiceImpl implements IService {
	@Override
	public void m1() {
		System.out.println("ServiceImpl m1...");
	}

	@Override
	public void m2() {
		System.out.println("ServiceImpl m2...");
	}

	@Override
	public void m3() {
		System.out.println("ServiceImpl m3...");
	}
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
```

结果表明：

- 代理的接口可以不是父类实现过的
- 代理类的全限定类名带有`$$EnhancerByCGLIB$$`字符
- 代理类继承父类ServiceImpl，同时实现了接口列表（IService、ServiceB）和Factory

```tex
doSomething
m1
class com.wrp.spring.framework.proxy.demo.ServiceImpl$$EnhancerByCGLIB$$9e6273da
parent: class com.wrp.spring.framework.proxy.demo.ServiceImpl
interface com.wrp.spring.framework.proxy.demo.IService
interface com.wrp.spring.framework.proxy.demo.ServiceB
interface org.springframework.cglib.proxy.Factory
```

### 3.6 cglib原理

在main方法第一行增加如下代码，会将cglib代理类文件存储在指定的目录下

```java
System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"E://temp");
```

```java
package com.wrp.spring.framework.proxy.demo;

import java.lang.reflect.Method;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class ServiceImpl$$EnhancerByCGLIB$$9e6273da extends ServiceImpl implements IService, ServiceB, Factory {
    private boolean CGLIB$BOUND;
    public static Object CGLIB$FACTORY_DATA;
    private static final ThreadLocal CGLIB$THREAD_CALLBACKS;
    private static final Callback[] CGLIB$STATIC_CALLBACKS;
    private MethodInterceptor CGLIB$CALLBACK_0;
    private static Object CGLIB$CALLBACK_FILTER;
    private static final Method CGLIB$m2$0$Method;
    private static final MethodProxy CGLIB$m2$0$Proxy;
    private static final Object[] CGLIB$emptyArgs;
    private static final Method CGLIB$m1$1$Method;
    private static final MethodProxy CGLIB$m1$1$Proxy;
    private static final Method CGLIB$m3$2$Method;
    private static final MethodProxy CGLIB$m3$2$Proxy;
    private static final Method CGLIB$equals$3$Method;
    private static final MethodProxy CGLIB$equals$3$Proxy;
    private static final Method CGLIB$toString$4$Method;
    private static final MethodProxy CGLIB$toString$4$Proxy;
    private static final Method CGLIB$hashCode$5$Method;
    private static final MethodProxy CGLIB$hashCode$5$Proxy;
    private static final Method CGLIB$clone$6$Method;
    private static final MethodProxy CGLIB$clone$6$Proxy;
    private static final Method CGLIB$doSomething$7$Method;
    private static final MethodProxy CGLIB$doSomething$7$Proxy;

    static void CGLIB$STATICHOOK1() {
        CGLIB$THREAD_CALLBACKS = new ThreadLocal();
        CGLIB$emptyArgs = new Object[0];
        Class var0 = Class.forName("com.wrp.spring.framework.proxy.demo.ServiceImpl$$EnhancerByCGLIB$$9e6273da");
        Class var1;
        Method[] var10000 = ReflectUtils.findMethods(new String[]{"equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;"}, (var1 = Class.forName("java.lang.Object")).getDeclaredMethods());
        CGLIB$equals$3$Method = var10000[0];
        CGLIB$equals$3$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$3");
        CGLIB$toString$4$Method = var10000[1];
        CGLIB$toString$4$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/String;", "toString", "CGLIB$toString$4");
        CGLIB$hashCode$5$Method = var10000[2];
        CGLIB$hashCode$5$Proxy = MethodProxy.create(var1, var0, "()I", "hashCode", "CGLIB$hashCode$5");
        CGLIB$clone$6$Method = var10000[3];
        CGLIB$clone$6$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/Object;", "clone", "CGLIB$clone$6");
        CGLIB$doSomething$7$Method = ReflectUtils.findMethods(new String[]{"doSomething", "()V"}, (var1 = Class.forName("com.wrp.spring.framework.proxy.demo.ServiceB")).getDeclaredMethods())[0];
        CGLIB$doSomething$7$Proxy = MethodProxy.create(var1, var0, "()V", "doSomething", "CGLIB$doSomething$7");
        var10000 = ReflectUtils.findMethods(new String[]{"m2", "()V", "m1", "()V", "m3", "()V"}, (var1 = Class.forName("com.wrp.spring.framework.proxy.demo.ServiceImpl")).getDeclaredMethods());
        CGLIB$m2$0$Method = var10000[0];
        CGLIB$m2$0$Proxy = MethodProxy.create(var1, var0, "()V", "m2", "CGLIB$m2$0");
        CGLIB$m1$1$Method = var10000[1];
        CGLIB$m1$1$Proxy = MethodProxy.create(var1, var0, "()V", "m1", "CGLIB$m1$1");
        CGLIB$m3$2$Method = var10000[2];
        CGLIB$m3$2$Proxy = MethodProxy.create(var1, var0, "()V", "m3", "CGLIB$m3$2");
    }

    final void CGLIB$m2$0() {
        super.m2();
    }

    public final void m2() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$m2$0$Method, CGLIB$emptyArgs, CGLIB$m2$0$Proxy);
        } else {
            super.m2();
        }
    }

    final void CGLIB$m1$1() {
        super.m1();
    }

    public final void m1() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$m1$1$Method, CGLIB$emptyArgs, CGLIB$m1$1$Proxy);
        } else {
            super.m1();
        }
    }

    final void CGLIB$m3$2() {
        super.m3();
    }

    public final void m3() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$m3$2$Method, CGLIB$emptyArgs, CGLIB$m3$2$Proxy);
        } else {
            super.m3();
        }
    }

    final boolean CGLIB$equals$3(Object var1) {
        return super.equals(var1);
    }

    public final boolean equals(Object var1) {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            Object var2 = var10000.intercept(this, CGLIB$equals$3$Method, new Object[]{var1}, CGLIB$equals$3$Proxy);
            return var2 == null ? false : (Boolean)var2;
        } else {
            return super.equals(var1);
        }
    }

    final String CGLIB$toString$4() {
        return super.toString();
    }

    public final String toString() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        return var10000 != null ? (String)var10000.intercept(this, CGLIB$toString$4$Method, CGLIB$emptyArgs, CGLIB$toString$4$Proxy) : super.toString();
    }

    final int CGLIB$hashCode$5() {
        return super.hashCode();
    }

    public final int hashCode() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            Object var1 = var10000.intercept(this, CGLIB$hashCode$5$Method, CGLIB$emptyArgs, CGLIB$hashCode$5$Proxy);
            return var1 == null ? 0 : ((Number)var1).intValue();
        } else {
            return super.hashCode();
        }
    }

    final Object CGLIB$clone$6() throws CloneNotSupportedException {
        return super.clone();
    }

    protected final Object clone() throws CloneNotSupportedException {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        return var10000 != null ? var10000.intercept(this, CGLIB$clone$6$Method, CGLIB$emptyArgs, CGLIB$clone$6$Proxy) : super.clone();
    }

    final void CGLIB$doSomething$7() {
        super.doSomething();
    }

    public final void doSomething() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$doSomething$7$Method, CGLIB$emptyArgs, CGLIB$doSomething$7$Proxy);
        } else {
            super.doSomething();
        }
    }

    public static MethodProxy CGLIB$findMethodProxy(Signature var0) {
        String var10000 = var0.toString();
        switch (var10000.hashCode()) {
            case -508378822:
                if (var10000.equals("clone()Ljava/lang/Object;")) {
                    return CGLIB$clone$6$Proxy;
                }
                break;
            case 102163345:
                if (var10000.equals("m1()V")) {
                    return CGLIB$m1$1$Proxy;
                }
                break;
            case 102193136:
                if (var10000.equals("m2()V")) {
                    return CGLIB$m2$0$Proxy;
                }
                break;
            case 102222927:
                if (var10000.equals("m3()V")) {
                    return CGLIB$m3$2$Proxy;
                }
                break;
            case 1826985398:
                if (var10000.equals("equals(Ljava/lang/Object;)Z")) {
                    return CGLIB$equals$3$Proxy;
                }
                break;
            case 1913648695:
                if (var10000.equals("toString()Ljava/lang/String;")) {
                    return CGLIB$toString$4$Proxy;
                }
                break;
            case 1984935277:
                if (var10000.equals("hashCode()I")) {
                    return CGLIB$hashCode$5$Proxy;
                }
                break;
            case 2121560294:
                if (var10000.equals("doSomething()V")) {
                    return CGLIB$doSomething$7$Proxy;
                }
        }

        return null;
    }

    public ServiceImpl$$EnhancerByCGLIB$$9e6273da() {
        CGLIB$BIND_CALLBACKS(this);
    }

    public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) {
        CGLIB$THREAD_CALLBACKS.set(var0);
    }

    public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] var0) {
        CGLIB$STATIC_CALLBACKS = var0;
    }

    private static final void CGLIB$BIND_CALLBACKS(Object var0) {
        ServiceImpl$$EnhancerByCGLIB$$9e6273da var1 = (ServiceImpl$$EnhancerByCGLIB$$9e6273da)var0;
        if (!var1.CGLIB$BOUND) {
            var1.CGLIB$BOUND = true;
            Object var10000 = CGLIB$THREAD_CALLBACKS.get();
            if (var10000 == null) {
                var10000 = CGLIB$STATIC_CALLBACKS;
                if (var10000 == null) {
                    return;
                }
            }

            var1.CGLIB$CALLBACK_0 = (MethodInterceptor)((Callback[])var10000)[0];
        }

    }

    public Object newInstance(Callback[] var1) {
        CGLIB$SET_THREAD_CALLBACKS(var1);
        ServiceImpl$$EnhancerByCGLIB$$9e6273da var10000 = new ServiceImpl$$EnhancerByCGLIB$$9e6273da();
        CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
        return var10000;
    }

    public Object newInstance(Callback var1) {
        CGLIB$SET_THREAD_CALLBACKS(new Callback[]{var1});
        ServiceImpl$$EnhancerByCGLIB$$9e6273da var10000 = new ServiceImpl$$EnhancerByCGLIB$$9e6273da();
        CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
        return var10000;
    }

    public Object newInstance(Class[] var1, Object[] var2, Callback[] var3) {
        CGLIB$SET_THREAD_CALLBACKS(var3);
        ServiceImpl$$EnhancerByCGLIB$$9e6273da var10000 = new ServiceImpl$$EnhancerByCGLIB$$9e6273da;
        switch (var1.length) {
            case 0:
                var10000.<init>();
                CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
                return var10000;
            default:
                throw new IllegalArgumentException("Constructor not found");
        }
    }

    public Callback getCallback(int var1) {
        CGLIB$BIND_CALLBACKS(this);
        MethodInterceptor var10000;
        switch (var1) {
            case 0:
                var10000 = this.CGLIB$CALLBACK_0;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }

    public void setCallback(int var1, Callback var2) {
        switch (var1) {
            case 0:
                this.CGLIB$CALLBACK_0 = (MethodInterceptor)var2;
            default:
        }
    }

    public Callback[] getCallbacks() {
        CGLIB$BIND_CALLBACKS(this);
        return new Callback[]{this.CGLIB$CALLBACK_0};
    }

    public void setCallbacks(Callback[] var1) {
        this.CGLIB$CALLBACK_0 = (MethodInterceptor)var1[0];
    }

    static {
        CGLIB$STATICHOOK1();
    }
}
```

### 3.7 NamingPolicy

> 生成CGLIB代理类的类名

```java
public interface NamingPolicy {

    String getClassName(String prefix, String source, Object key, Predicate names);
}
```

1. 默认的实现类，规则：`被代理class name + "$$" + 使用cglib处理的class name + "ByCGLIB" + "$$" + key的hashcode`

> 如`com.wrp.spring.framework.proxy.demo.ServiceImpl$$EnhancerByCGLIB$$9e6273da`

```java
public class DefaultNamingPolicy implements NamingPolicy {
    public static final DefaultNamingPolicy INSTANCE = new DefaultNamingPolicy();

    /**
     * This allows to test collisions of {@code key.hashCode()}.
     */
    private static final boolean STRESS_HASH_CODE = Boolean.getBoolean("org.springframework.cglib.test.stressHashCodes");

    @Override
    public String getClassName(String prefix, String source, Object key, Predicate names) {
        if (prefix == null) {
            prefix = "org.springframework.cglib.empty.Object";
        } else if (prefix.startsWith("java")) {
            prefix = "$" + prefix;
        }
        String base =
            prefix + "$$" +
            source.substring(source.lastIndexOf('.') + 1) +
            getTag() + "$$" +
            Integer.toHexString(STRESS_HASH_CODE ? 0 : key.hashCode());
        String attempt = base;
        int index = 2;
        while (names.evaluate(attempt)) {
          attempt = base + "_" + index++;
       }
        return attempt;
    }

    /**
     * Returns a string which is incorporated into every generated class name.
     * By default returns "ByCGLIB"
     */
    protected String getTag() {
        return "ByCGLIB";
    }

    @Override
    public int hashCode() {
        return getTag().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DefaultNamingPolicy defaultNamingPolicy) &&
                defaultNamingPolicy.getTag().equals(getTag());
    }
}
```

2. Spring实现

```java
public final class SpringNamingPolicy implements NamingPolicy {

    public static final SpringNamingPolicy INSTANCE = new SpringNamingPolicy();

    private static final String SPRING_LABEL = "$$SpringCGLIB$$";

    private static final String FAST_CLASS_SUFFIX = "FastClass$$";


    private SpringNamingPolicy() {
    }

    @Override
    public String getClassName(String prefix, String source, Object key, Predicate names) {
       if (prefix == null) {
          prefix = "org.springframework.cglib.empty.Object";
       }
       else if (prefix.startsWith("java.") || prefix.startsWith("javax.")) {
          prefix = "_" + prefix;
       }

       String base;
       int existingLabel = prefix.indexOf(SPRING_LABEL);
       if (existingLabel >= 0) {
          base = prefix.substring(0, existingLabel + SPRING_LABEL.length());
       }
       else {
          base = prefix + SPRING_LABEL;
       }

       // When the generated class name is for a FastClass, the source is
       // "org.springframework.cglib.reflect.FastClass".
       boolean isFastClass = (source != null && source.endsWith(".FastClass"));
       if (isFastClass && !prefix.contains(FAST_CLASS_SUFFIX)) {
          base += FAST_CLASS_SUFFIX;
       }

       int index = 0;
       String attempt = base + index;
       while (names.evaluate(attempt)) {
          attempt = base + index++;
       }
       return attempt;
    }

}
```

3. 自定义名称策略

结果： `class com.wrp.spring.framework.proxy.demo.ServiceImpl$$Enhancer-test-$$a1cba1a0`

```java
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
```

### 3.8 Objenesis

> 不使用构造方法创建对象
>
> `Objenesis.newInstance`方法不调用构造器方法

```java
public class User {

    private String name;

    private User() {
       throw new RuntimeException("不要通过反射创建User对象");
    }

    public User(String name) {
       this.name = name;
    }

    @Override
    public String toString() {
       return "User{" +
             "name='" + name + '\'' +
             '}';
    }
}
```

```java
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
```
