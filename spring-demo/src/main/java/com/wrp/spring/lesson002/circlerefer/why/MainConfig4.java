package com.wrp.spring.lesson002.circlerefer.why;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-24 08:05
 **/
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(value = MethodBeforeInterceptor.class, type = FilterType.ASSIGNABLE_TYPE)
})
public class MainConfig4 {
}
