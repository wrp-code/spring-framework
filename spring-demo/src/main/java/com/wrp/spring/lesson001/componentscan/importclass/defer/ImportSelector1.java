package com.wrp.spring.lesson001.componentscan.importclass.defer;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 14:45
 **/
public class ImportSelector1 implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{
				Configuration1.class.getName()
		};
	}
}