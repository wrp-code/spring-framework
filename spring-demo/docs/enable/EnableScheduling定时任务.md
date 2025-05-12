`@EnableScheduling`可以开启定时任务功能。

```java
@Configuration
@EnableScheduling
public class Config {
}
```

## 1. 使用方法

### 1.1 方法1 cron表达式

> cron表达式：用于指定定时任务执行时间的字符串表达式。
>
> [在线表达式生成器](!https://cron.ciding.cc/)

------

```tex
            ┌──────────── [可选] 秒 (0 - 59)
            | ┌────────── 分钟 (0 - 59)
            | | ┌──────── 小时 (0 - 23)
            | | | ┌────── 天数 (1 - 31)
            | | | | ┌──── 月份 (1 - 12) OR jan,feb,mar,apr ...
            | | | | | ┌── 星期几 (0 - 6, 星期天 = 0) OR sun,mon ...
            | | | | | |
            * * * * * * 命令
```

```java
// 每秒执行一次任务
@Scheduled(cron = "0/1 * * * * ?") 
public void task0() throws InterruptedException {
    System.out.println("config: " + Thread.currentThread().getName());
}
```

### 1.2 方法2 fixedRate

> 按照指定速率执行，从方法执行的开始时间算

```java
// 默认单位是毫秒，3000毫秒执行一次任务
@Scheduled(fixedRate = 3000)
public void task1() throws InterruptedException {
    System.out.println("fixedRate = 3000, " + System.currentTimeMillis());
}

// 指定单位，3s执行一次任务
@Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
public void task2() {
    System.out.println("fixedRate = 3, " + System.currentTimeMillis());
}
```

### 1.3 方法3 fixedDelay

> 按照指定延迟执行，从方法执行完成时间算

```java
// 5000毫秒执行一次任务
@Scheduled(fixedDelay = 5000)
public void task3() {
    System.out.println("fixedDelay = 5000, "+ System.currentTimeMillis());
}
```

### 1.4 方法4 initialDelay

> 初始延迟时间，当不指定cron、fixedRate、fixedDelay时，此任务为单次任务

```java
// 系统启动2000毫秒后执行一次任务
@Scheduled(initialDelay = 2000)
public void task4() {
    System.out.println("@Scheduled");
}
```

当不指定任务参数时，报错：Encountered invalid @Scheduled method 'task4': One-time task only supported with specified initial delay。

```java
@Scheduled
public void task4() {
    System.out.println("@Scheduled");
}
```

## 2. 核心原理

1. `EnableScheduling`注解通过`@Import`注解导入`SchedulingConfiguration`配置类

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SchedulingConfiguration.class)
@Documented
public @interface EnableScheduling {
}
```

2. `SchedulingConfiguration`配置类导入`ScheduledAnnotationBeanPostProcessor`类，`ScheduledAnnotationBeanPostProcessor`是一个`BeanPostProcessor`，会参与到Bean的生命周期中

```java
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SchedulingConfiguration {

	@Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
		return new ScheduledAnnotationBeanPostProcessor();
	}
}
```

3. `ScheduledAnnotationBeanPostProcessor#postProcessAfterInitialization`解析配置类，将带`@Scheduled`注解的方法缓存起来

4. `ScheduledAnnotationBeanPostProcessor`类监听了`ApplicationContextEvent`事件，会根据`ApplicationContext`的生命周期进行定时任务的启动和取消

```java
@Override
public void onApplicationEvent(ApplicationContextEvent event) {
    if (event.getApplicationContext() == this.applicationContext) {
        // ApplicationContext完成后会发布ContextRefreshedEvent，进行定时任务解析
        if (event instanceof ContextRefreshedEvent) {
            finishRegistration();
        }
        // ApplicationContext关闭时发布ContextClosedEvent，进行定时任务取消
        else if (event instanceof ContextClosedEvent) {
            for (Object bean : this.manualCancellationOnContextClose) {
                cancelScheduledTasks(bean);
            }
            this.manualCancellationOnContextClose.clear();
        }
    }
}
```



## 3. 源码分析



## 4. 常见问题

### 4.1 三种主要方式的区别

1. `cron` 类似`fixedDelay`，可以写表达式，更灵活
2. `fixedRate` 按照指定时间间隔发布任务，会缓存被耽搁的任务，上一次任务完成后，优先执行被耽搁的任务
3. `fixedDelay` 按照方法上一次执行完成时间开始，指定延迟时间后再次执行。

测试1

```java
int count = 0;

@Scheduled(fixedRate = 3000)
public void task5() throws InterruptedException {
    if(count == 3) {
        TimeUnit.SECONDS.sleep(10);
    }
    System.out.println("fixedRate = 3000, " + System.currentTimeMillis() + " count: " + count++);
}
```

从结果看出：

1. 定时任务按照每三秒的间隔执行一次
2. count:2与count:3的间隔为13秒 （3秒间隔 + 10秒sleep）
3. count3执行后，优先执行因为上一次任务执行时间过长而耽搁的任务count4、count5、count6
4. count7与count6之间的间隔不是3秒，因为count7是按照全局3秒间隔来计算的

```tex
fixedRate = 3000, 1747037688756count: 0
fixedRate = 3000, 1747037691753count: 1
fixedRate = 3000, 1747037694744count: 2
fixedRate = 3000, 1747037707754count: 3
fixedRate = 3000, 1747037707754count: 4
fixedRate = 3000, 1747037707754count: 5
fixedRate = 3000, 1747037707754count: 6
fixedRate = 3000, 1747037709746count: 7
fixedRate = 3000, 1747037712753count: 8
fixedRate = 3000, 1747037715744count: 9
fixedRate = 3000, 1747037718755count: 10
```

测试2

```java
int count = 0;

@Scheduled(fixedDelay = 1000)
public void task6() throws InterruptedException {
    if(count == 3) {
       TimeUnit.SECONDS.sleep(10);
    }
    System.out.println("fixedDelay = 1000, "+ System.currentTimeMillis() + " count: " + count++);
}
```

从结果看出：

1. 定时任务按照每秒的间隔执行一次
2. count2与count3的间隔为11秒（1秒间隔 + 10秒sleep）
3. count3之后的间隔都是1秒

```tex
fixedDelay = 1000, 1747038418194 count: 0
fixedDelay = 1000, 1747038419209 count: 1
fixedDelay = 1000, 1747038420218 count: 2
fixedDelay = 1000, 1747038431230 count: 3
fixedDelay = 1000, 1747038432239 count: 4
fixedDelay = 1000, 1747038433243 count: 5
fixedDelay = 1000, 1747038434247 count: 6
fixedDelay = 1000, 1747038435261 count: 7
fixedDelay = 1000, 1747038436273 count: 8
fixedDelay = 1000, 1747038437274 count: 9
```

### 4.2 定时任务不执行的场景



## 5. 扩展知识







