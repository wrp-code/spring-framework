package com.wrp.spring.framework.componentscan.demo1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-05-16 07:32
 **/
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = MyComponent.class)
})
public class Config {
}
