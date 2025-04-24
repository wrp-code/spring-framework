package com.wrp.spring.lesson001.componentscan;

import com.wrp.spring.lesson001.componentscan.scanclass.MyFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-19 09:07
 **/
@ComponentScan(
		basePackages = "com.wrp.spring.lesson001.componentscan.scanclass",
		useDefaultFilters = false,
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyFilter.class)
		}
)
public class ScanBean5 {
}
