package com.wrp.spring.lesson001.componentscan;

import com.wrp.spring.lesson001.componentscan.custom.MyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wrp
 * @since 2025-04-19 08:51
 **/
@ComponentScan(
		basePackages = "com.wrp.spring.lesson001.componentscan.custom",
		useDefaultFilters = false,// 不扫描@Component等默认注解注册到容器， 不影响@AliasFor
		includeFilters = {
			@ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyBean.class)
})
public class ScanBean3 {
}
