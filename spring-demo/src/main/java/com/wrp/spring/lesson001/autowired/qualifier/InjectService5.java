package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 19:54
 **/
@Component
public class InjectService5 {
	@Autowired
	private IService service1; //@1

	@Override
	public String toString() {
		return "InjectService{" +
				"service1=" + service1 +
				'}';
	}
}
