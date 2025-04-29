package com.wrp.spring.lesson003.readwrite.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wrp
 * @since 2025年04月29日 14:50
 **/
@Configuration
@EnableAspectJAutoProxy //@1
@EnableTransactionManagement(proxyTargetClass = true, order = Integer.MAX_VALUE - 1) //@2
@ComponentScan
public class ReadWriteConfiguration {
}
