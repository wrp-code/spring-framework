package com.wrp.spring.framework.autowirecandidate;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wrp
 * @since 2025-05-07 23:47
 **/
public class C {

	@Autowired
	List<A> a;
}
