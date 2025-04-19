package com.wrp.spring.lesson001.componentscan.scanclass;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author wrp
 * @since 2025-04-19 09:06
 **/
public class MyFilter implements TypeFilter {
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		Class curClass = null;
		try {
			//当前被扫描的类
			curClass = Class.forName(metadataReader.getClassMetadata().getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//判断curClass是否是IService类型
		return IService.class.isAssignableFrom(curClass);
	}
}
