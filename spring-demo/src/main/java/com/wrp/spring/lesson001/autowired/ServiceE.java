package com.wrp.spring.lesson001.autowired;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:30
 **/
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)//@
public class ServiceE {
}
