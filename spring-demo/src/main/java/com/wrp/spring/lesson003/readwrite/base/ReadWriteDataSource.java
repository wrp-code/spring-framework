package com.wrp.spring.lesson003.readwrite.base;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author wrp
 * @since 2025年04月29日 14:42
 **/
public class ReadWriteDataSource  extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DsTypeHolder.getDsType();
	}
}