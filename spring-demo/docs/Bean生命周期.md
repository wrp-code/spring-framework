## 1. Spring IOC容器
> 本质上就是对象创建过程的一个封装，实例化、初始化，在这两个阶段的前后增加一些扩展点。
> 

## 2. 元信息阶段
> 元信息在Spring容器中对应的BeanDefinition，此阶段主要包括元信息的配置、元信息的解析、元信息的注册等步骤
>
细分如下阶段：

1. BeanDefinition配置阶段
2. BeanDefinition解析阶段
3. BeanDefinition注册阶段
4. BeanDefinition合并阶段
5. Bean Class类加载阶段

### 2.1 BeanDefinition配置阶段

#### 2.1.1 XMl方式



#### 2.1.2 API方式



#### 2.1.3 注解方式



#### 2.1.4 Properties方式

> 用的不多





## 3. Bean实例化阶段

> 实例化阶段的核心逻辑是使用反射创建对象。
> 
细分如下阶段：
1. Bean实例化前阶段
2. Bean实例化阶段
3. 合并后的BeanDefinition处理
4. 实例化后阶段

## 4. Bean属性赋值阶段
> 通过反射给字段赋值
> 
1. Bean属性赋值前阶段
2. Bean属性赋值阶段

## 5. Bean初始化阶段
> 调用一系列的回调接口
1. Bean Aware接口回调阶段
2. Bean初始化前阶段
3. Bean初始化阶段
4. Bean初始化后阶段
5. 所有单例Bean初始化完成后阶段

## 6. Bean使用阶段
> 从Spring容器中获取Bean对象
> 

## 7. Bean销毁阶段
> Bean使用完后，需要对其资源进行释放
1. Bean销毁前阶段
2. Bean销毁阶段
