package com.wrp.spring.lesson001.componentscan.importclass.defer;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 14:45
 **/
public class DeferredImportSelector1 implements DeferredImportSelector, Ordered {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{Configuration2.class.getName()};
	}

	@Override
	public int getOrder() {
		return 2;
	}
}