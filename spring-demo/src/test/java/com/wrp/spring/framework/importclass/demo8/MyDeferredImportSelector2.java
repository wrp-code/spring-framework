package com.wrp.spring.framework.importclass.demo8;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025-05-19 08:05
 **/
public class MyDeferredImportSelector2 implements DeferredImportSelector, Ordered {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{Config2.class.getName()};
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
