package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 19:53
 **/
@Component
public class InjectService4 {
	private IService s1;
	private IService s2;

	@Autowired
	@Qualifier("service2")
	public void setS1(IService s1) {
		this.s1 = s1;
	}

	@Autowired
	@Qualifier("service2")
	public void setS2(IService s2) {
		this.s2 = s2;
	}

	@Override
	public String toString() {
		return "InjectService{" +
				"s1=" + s1 +
				", s2=" + s2 +
				'}';
	}
}
