package com.wrp.spring.lesson001.componentscan.importclass.costtime;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wrp
 * @since 2025年04月21日 14:35
 **/
public class MethodCostTimeImportSelector implements ImportSelector {
	@Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{MethodCostTimeProxyBeanPostProcessor.class.getName()};
	}
}
