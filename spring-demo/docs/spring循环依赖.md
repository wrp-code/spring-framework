> 1. Spring无法解决构造器注入的循环依赖。
> 2. 仅先获取set方法注入的单例时，依赖才能注入成功。

## 1. 情况分类

### 1.1 bean分类

- 多例 + 多例
- 单例 + 多例
- 单例 + 单例

### 2.2 注入方法分类

- 构造器注入
- set注入
- 混合注入

## 2. 测试

```java
public class Service1 {

    Service2 service2;

    public Service1() {
    }

    public Service1(Service2 service2) {
        this.service2 = service2;
    }

    public void setService2(Service2 service2) {
        this.service2 = service2;
    }

    public Service2 getService2() {
        return service2;
    }
}

public class Service2 {

    Service1 service1;

    public Service2() {
    }

    public Service2(Service1 service1) {
        this.service1 = service1;
    }

    public void setService1(Service1 service1) {
        this.service1 = service1;
    }

    public Service1 getService1() {
        return service1;
    }
}
```

### 2.1 多例 + 多例 + 构造器注入

结论：无论获取哪个Bean均报错`org.springframework.beans.factory.BeanCreationException`

```java
    @Test
    public void test1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.2 多例 + 多例 + set注入

> 结论：无论获取哪个Bean均报错`org.springframework.beans.factory.BeanCreationException`

```java
    @Test
    public void test3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test4() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.3 多例 + 多例 + 混合注入

> 结论：无论获取哪个Bean均报错`org.springframework.beans.factory.BeanCreationException`

```java
	@Test
    public void test5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test6() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.4 单例 + 多例 + 构造器注入

> 结论：无论获取哪个Bean均报错`org.springframework.beans.factory.BeanCreationException`

```java
    @Test
    public void test7() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test8() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.5 单例 + 多例 + set注入

结论：

- 先获取单例Service1，再获取多例Service2，不报错，注入到Service1中的Service2与获取的Service2不同。
- 先获取多例Service2，报错`org.springframework.beans.factory.BeanCreationException`

```java
    @Test
    public void test9() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Service1 service1 = beanFactory.getBean(Service1.class);
        Service2 service2 = beanFactory.getBean(Service2.class);
        Assertions.assertEquals(service1, service2.getService1());
        Assertions.assertNotEquals(service1.getService2(), service2);
    }

    @Test
    public void test10() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.6 单例 + 多例 + 混合注入

结论：

- 单例Bean使用set注入，多例Bean使用构造器注入，先获取单例Service1，不报错，注入到Service1中的Service2与获取的Service2不同。
- 单例Bean使用set注入，多例Bean使用构造器注入，先获取单例Service2，报错`org.springframework.beans.factory.BeanCreationException`。
- 单例Bean使用构造器注入，多例Bean使用set注入，先获取单例Service1，报错`org.springframework.beans.factory.BeanCreationException`。
- 单例Bean使用构造器注入，多例Bean使用set注入，先获取单例Service2，报错`org.springframework.beans.factory.BeanCreationException`。

```java
	@Test
    public void test11() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Service1 service1 = beanFactory.getBean(Service1.class);
        Service2 service2 = beanFactory.getBean(Service2.class);
        Assertions.assertEquals(service1, service2.getService1());
        Assertions.assertNotEquals(service1.getService2(), service2);
    }

    @Test
    public void test12() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }

	@Test
    public void test13() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test14() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.7 单例 + 单例 + 构造器注入

结论：获取任意Bean均报错`org.springframework.beans.factory.BeanCreationException`

```java
    @Test
    public void test15() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test16() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service2.class));
    }
```

### 2.8 单例 + 单例 + set注入

结论： 注入成功，且注入的Bean与Spring容器中的Bean相同。

```java
    @Test
    public void test17() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Service1 service1 = beanFactory.getBean(Service1.class);
        Service2 service2 = beanFactory.getBean(Service2.class);
        Assertions.assertEquals(service1, service2.getService1());
        Assertions.assertEquals(service2, service1.getService2());
    }

    @Test
    public void test18() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service2", "service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Service2 service2 = beanFactory.getBean(Service2.class);
        Service1 service1 = beanFactory.getBean(Service1.class);
        Assertions.assertEquals(service1, service2.getService1());
        Assertions.assertEquals(service2, service1.getService2());
    }
```

### 2.9 单例 + 单例 + 混合注入

结论： 

- 先获取构造器注入的单例Bean，报错`org.springframework.beans.factory.BeanCreationException`
- 先获取set注入的单例Bean，注入正常

```java
    @Test
    public void test19() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Assertions.assertThrows(BeanCreationException.class, () -> beanFactory.getBean(Service1.class));
    }

    @Test
    public void test20() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addConstructorArgReference("service2")
                        .getBeanDefinition());
        beanFactory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                        .addPropertyReference("service1", "service1")
                        .getBeanDefinition());
        Service2 service2 = beanFactory.getBean(Service2.class);
        Service1 service1 = beanFactory.getBean(Service1.class);
        Assertions.assertEquals(service1, service2.getService1());
        Assertions.assertEquals(service2, service1.getService2());
    }
```

## 3. 总结

| 用例          | 注入方式                | getBean顺序 | 单元测试 | 结果     |
| ------------- | ----------------------- | ----------- | -------- | -------- |
| 多例A + 多例B | 构造器注入              | 先获取A     | test1    | 报错     |
|               | 构造器注入              | 先获取B     | test2    | 报错     |
|               | set注入                 | 先获取A     | test3    | 报错     |
|               | set注入                 | 先获取B     | test4    | 报错     |
|               | 混合注入(Aset；B构造器) | 先获取A     | test5    | 报错     |
|               | 混合注入(Aset；B构造器) | 先获取B     | test6    | 报错     |
| 单例A + 多例B | 构造器注入              | 先获取A     | test7    | 报错     |
|               | 构造器注入              | 先获取B     | test8    | 报错     |
|               | set注入                 | 先获取A     | test9    | 注入成功 |
|               | set注入                 | 先获取B     | test10   | 报错     |
|               | 混合注入(Aset；B构造器) | 先获取A     | test11   | 注入成功 |
|               | 混合注入(Aset；B构造器) | 先获取B     | test12   | 报错     |
|               | 混合注入(A构造器；Bset) | 先获取A     | test13   | 报错     |
|               | 混合注入(A构造器；Bset) | 先获取B     | test14   | 报错     |
| 单例A + 单例B | 构造器注入              | 先获取A     | test15   | 报错     |
|               | 构造器注入              | 先获取B     | test16   | 报错     |
|               | set注入                 | 先获取A     | test17   | 注入成功 |
|               | set注入                 | 先获取B     | test18   | 注入成功 |
|               | 混合注入(A构造器；Bset) | 先获取A     | test19   | 报错     |
|               | 混合注入(A构造器；Bset) | 先获取B     | test20   | 注入成功 |

1. Spring无法解决构造器注入的循环依赖。
2. 仅先获取set方法注入的单例时，依赖才能注入成功。