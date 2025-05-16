package com.wrp.spring.framework.componentscan.demo4;

import com.wrp.spring.framework.componentscan.demo3.IService;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author wrp
 * @since 2025-05-16 07:53
 **/
public class MyTypeFilter implements TypeFilter {
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		String className = metadataReader.getClassMetadata().getClassName();
		try {
			return Class.forName(className).isAssignableFrom(IService.class);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
