package com.wrp.spring.framework.componentscan.demo4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-05-16 07:41
 **/
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.CUSTOM,
			value = MyTypeFilter.class)
})
public class Config4 {
}
