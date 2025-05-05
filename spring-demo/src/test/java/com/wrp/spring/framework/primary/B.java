package com.wrp.spring.framework.primary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年05月05日 18:03
 */
@Component
public class B {

	@Autowired
	A a;
}
