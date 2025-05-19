package com.wrp.spring.framework.importclass.demo7;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025-05-19 07:59
 **/
public class MyImportSelector implements ImportSelector {


	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{Config2.class.getName()};
	}
}
