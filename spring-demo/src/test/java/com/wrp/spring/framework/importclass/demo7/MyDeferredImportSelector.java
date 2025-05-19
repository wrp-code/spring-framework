package com.wrp.spring.framework.importclass.demo7;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025-05-19 08:00
 **/
public class MyDeferredImportSelector implements DeferredImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{Config3.class.getName()};
	}
}
