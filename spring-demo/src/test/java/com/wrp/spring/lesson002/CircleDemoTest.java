package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.circlerefer.demo.Service1;
import com.wrp.spring.lesson002.circlerefer.demo.Service2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author wrp
 * @since 2025年04月24日 13:55
 **/
public class CircleDemoTest {

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
}
