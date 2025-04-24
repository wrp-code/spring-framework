package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月21日 16:44
 **/
@Component
public class InjectService {

	@Autowired
	@Qualifier("tag1") //@1
	private Map<String, IService> serviceMap1;

	@Autowired
	@Qualifier("tag2") //@2
	private Map<String, IService> serviceMap2;

	public void setServiceMap1(Map<String, IService> serviceMap1) {
		this.serviceMap1 = serviceMap1;
	}

	public void setServiceMap2(Map<String, IService> serviceMap2) {
		this.serviceMap2 = serviceMap2;
	}

	@Override
	public String toString() {
		return "InjectService{" +
				"serviceMap1=" + serviceMap1 +
				", serviceMap2=" + serviceMap2 +
				'}';
	}
}
