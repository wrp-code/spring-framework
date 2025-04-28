package com.wrp.spring.lesson003.transaction_ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025年04月28日 13:52
 **/
@Component
public class Tx2Service {
	@Autowired
	private Ds2User1Service ds2User1Service;
	@Autowired
	private Ds2User2Service ds2User2Service;

	@Transactional(transactionManager = "transactionManager2", propagation = Propagation.REQUIRED)
	public void test1() {
		this.ds2User1Service.required("王五");
		this.ds2User2Service.required("赵六");
	}

	@Transactional(transactionManager = "transactionManager2", propagation = Propagation.REQUIRED)
	public void test2() {
		this.ds2User1Service.required("王五");
		this.ds2User2Service.required("赵六");
		throw new RuntimeException();
	}
}