package com.wrp.spring.lesson001.dependon;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-21 20:32
 **/
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DisposableBean.class)
})
@Configuration
public class MainConfig2 {
}