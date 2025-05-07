## 1. 元信息BeanDefinition阶段 

BeanDefinition体系

重要属性

- beanclass
- factoryBeanName +factoryMethodName
- initMethodName
- destroyMethodName
- scope
- depend-on
- primary
- autowire-candidate
- lazy-init
- abstract + parentName

## 2. 依赖注入DI

构造器注入

手动注入

自动注入

lookup-method

replaced-method

## 3. 批量注册Bean

@Configuration +@Bean

@ComponentScan + @Component

@Import

@Conditional条件注入

其他常用注入

- @Autowired
- @Resource
- @Primary
- @Qulifier
- @Scope
- @DependsOn
- @ImportResource
- @Lazy

## 4. Bean的生命周期



## 5. 父子容器



## 6. @Value



## 7. 国际化



## 8. Spring事件机制

## 9. 循环依赖

## 10. BeanFactoryPostProcessor



## 11. AOP

jdk动态代理

CGLIB动态代理

Aop基础

Aop源码

AspectJ

## 12. Enable系列

@EnableAsync

@EnableScheduling

@EnableCaching



## 14. Spel



## 15. 事务

jdbcTemplate

编程式事务

声明式事务

事务的7种传播行为

多数据源事务

事务源码

事务拦截器的顺序

事务失效的几种情况



## 16. Spring应用

数据库读写分离

集成Mybatis

集成Junit



## 17. Spring上下文生命周期