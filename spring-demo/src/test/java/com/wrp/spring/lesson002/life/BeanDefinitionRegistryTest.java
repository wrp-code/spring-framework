package com.wrp.spring.lesson002.life;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Arrays;

/**
 * 阶段三、BeanDefinition注册阶段
 * 注册原始的BeanDefinition,存储在DefaultListableBeanFactory
 * @author wrp
 * @since 2025-04-21 21:35
 **/
public class BeanDefinitionRegistryTest {

	@Test
	public void test1() {
		//创建一个bean工厂，这个默认实现了BeanDefinitionRegistry接口，所以也是一个bean注册器
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

		//定义一个bean
		GenericBeanDefinition nameBdf = new GenericBeanDefinition();
		nameBdf.setBeanClass(String.class);
		nameBdf.getConstructorArgumentValues().addIndexedArgumentValue(0, "路人甲Java");

		//将bean注册到容器中
		factory.registerBeanDefinition("name", nameBdf);

		//通过名称获取BeanDefinition
		System.out.println(factory.getBeanDefinition("name"));
		//通过名称判断是否注册过BeanDefinition
		System.out.println(factory.containsBeanDefinition("name"));
		//获取所有注册的名称
		System.out.println(Arrays.asList(factory.getBeanDefinitionNames()));
		//获取已注册的BeanDefinition的数量
		System.out.println(factory.getBeanDefinitionCount());
		//判断指定的name是否使用过
		System.out.println(factory.isBeanNameInUse("name"));

		//别名相关方法
		//为name注册2个别名
		factory.registerAlias("name", "alias-name-1");
		factory.registerAlias("name", "alias-name-2");

		//判断alias-name-1是否已被作为别名使用
		System.out.println(factory.isAlias("alias-name-1"));

		//通过名称获取对应的所有别名
		System.out.println(Arrays.asList(factory.getAliases("name")));

		//最后我们再来获取一下这个bean
		System.out.println(factory.getBean("name"));


	}
}
