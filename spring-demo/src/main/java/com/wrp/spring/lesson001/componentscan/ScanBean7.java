package com.wrp.spring.lesson001.componentscan;

import com.wrp.spring.lesson001.componentscan.beans.ScanClass;
import com.wrp.spring.lesson001.componentscan.scanclass.IService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-19 09:10
 **/
@ComponentScan(basePackageClasses = ScanClass.class)
@ComponentScan(
		useDefaultFilters = false, //不启用默认过滤器
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IService.class)
		})
public class ScanBean7 {
}
