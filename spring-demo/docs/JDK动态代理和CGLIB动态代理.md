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



## 3. CGLIB动态代理
