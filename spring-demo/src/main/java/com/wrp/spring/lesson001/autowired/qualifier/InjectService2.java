package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 16:46
 **/
@Component
public class InjectService2 {
	@Autowired
	@Qualifier("service2") //@1
	private IService service;

	@Override
	public String toString() {
		return "InjectService{" +
				"service=" + service +
				'}';
	}
}