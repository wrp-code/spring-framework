package com.wrp.spring.lesson002.life;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @author wrp
 * @since 2025-04-21 21:28
 **/
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Primary
@Lazy
public class Service1 {
}
