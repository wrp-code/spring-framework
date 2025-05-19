package com.wrp.spring.framework.importclass.demo8;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025-05-19 08:07
 **/
@Import({
		MyDeferredImportSelector.class,
		MyDeferredImportSelector2.class
})
public class DemoConfig8 {
}
