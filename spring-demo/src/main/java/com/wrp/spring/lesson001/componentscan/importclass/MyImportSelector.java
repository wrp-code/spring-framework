package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 14:27
 **/
public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{
				Service1.class.getName(),
				ConfigModule2.class.getName(),
				Service2.class.getName()
		};
	}
}
