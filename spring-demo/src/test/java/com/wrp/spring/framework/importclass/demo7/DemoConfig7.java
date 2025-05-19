package com.wrp.spring.framework.importclass.demo7;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025-05-19 08:01
 **/
// 导入的Bean如果名称相同，后面的会覆盖前面的
@Import({
		MyDeferredImportSelector.class,
		Config1.class,
		MyImportSelector.class
})
public class DemoConfig7 {
}
