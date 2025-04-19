package com.wrp.spring.lesson001.componentscan;

import com.wrp.spring.lesson001.componentscan.scanclass.IService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-19 09:00
 **/
@ComponentScan(
		useDefaultFilters = false,
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IService.class)
		}
)
public class ScanBean4 {
}
