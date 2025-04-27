package com.wrp.spring.lesson003.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wrp
 * @since 2025-04-27 23:05
 **/
@Component
public class TxService {

	@Autowired
	Service1 user1Service;

	@Autowired
	Service2 user2Service;

	/**
	 * ==============================================================
	 * 	场景1：外围方法没有事务，外围方法内部调用2个REQUIRED级别的事务方法。
	 * ==============================================================
	 */

	// 外围方法未开启事务，插入“张三”、“李四”方法在自己的事务中独立运行，
	// 外围方法异常不影响内部插入“张三”、“李四”方法独立的事务。
	public void notransaction_exception_required_required() {
		this.user1Service.required("张三");
		this.user2Service.required("李四");
		throw new RuntimeException();
	}

	// 外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行，
	// 所以插入“李四”方法抛出异常只会回滚插入“李四”方法，插入“张三”方法不受影响。
	public void notransaction_required_required_exception() {
		this.user1Service.required("张三");
		this.user2Service.required_exception("李四");
	}

	/**
	 * ==============================================================
	 * 	场景1：外围方法开启事务（Propagation.REQUIRED），这个使用频率特别高。
	 * ==============================================================
	 */

	// 外围方法开启事务，内部方法加入外围方法事务，
	// 外围方法回滚，内部方法也要回滚。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_required() {
		user1Service.required("张三");
		user2Service.required("李四");
		throw new RuntimeException();
	}

	// 外围方法开启事务，内部方法加入外围方法事务，
	// 内部方法抛出异常回滚，外围方法感知异常致使整体事务回滚。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception() {
		user1Service.required("张三");
		user2Service.required_exception("李四");
	}

	// 外围方法开启事务，内部方法加入外围方法事务，
	// 内部方法抛出异常回滚，即使方法被catch不被外围方法感知，整个事务依然回滚。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception_try() {
		user1Service.required("张三");
		try {
			user2Service.required_exception("李四");
		} catch (Exception e) {
			System.out.println("方法回滚");
		}
	}
}
