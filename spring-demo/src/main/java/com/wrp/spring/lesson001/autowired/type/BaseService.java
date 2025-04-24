package com.wrp.spring.lesson001.autowired.type;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wrp
 * @since 2025-04-21 20:00
 **/
public class BaseService<T> {
	@Autowired
	private IDao<T> dao; //@1

	public IDao<T> getDao() {
		return dao;
	}

	public void setDao(IDao<T> dao) {
		this.dao = dao;
	}
}