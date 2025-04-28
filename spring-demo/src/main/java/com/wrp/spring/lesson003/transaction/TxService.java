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
	 * 	场景1.1：外围方法没有事务，外围方法内部调用2个REQUIRED级别的事务方法。
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
	 * 	场景1.2：外围方法开启事务（Propagation.REQUIRED），这个使用频率特别高。
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

	/**
	 * ==============================================================
	 * 	场景2.1：外围方法没有事务,内部调用两个request_new方法
	 * ==============================================================
	 */

	// 外围方法没有事务，
	// 插入“张三”、“李四”方法都在自己的事务中独立运行,
	// 外围方法抛出异常回滚不会影响内部方法。
	public void notransaction_exception_requiresNew_requiresNew(){
		user1Service.requires_new("张三");
		user2Service.requires_new("李四");
		throw new RuntimeException();
	}

	// 外围方法没有开启事务，
	// 插入“张三”方法和插入“李四”方法分别开启自己的事务，
	// 插入“李四”方法抛出异常回滚，其他事务不受影响。
	public void notransaction_requiresNew_requiresNew_exception(){
		user1Service.requires_new("张三");
		user2Service.requires_new_exception("李四");
	}

	/**
	 * ==============================================================
	 * 	场景2.2：外围方法有事务,内部调用两个request_new方法
	 * ==============================================================
	 */

	// 外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、
	// 插入“王五”方法分别在独立的新建事务中，
	// 外围方法抛出异常只回滚和外围方法同一事务的方法，故插入“张三”的方法回滚。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_requiresNew_requiresNew() {
		user1Service.required("张三");

		user2Service.requires_new("李四");

		user2Service.requires_new("王五");
		throw new RuntimeException();
	}

	// 外围方法开启事务，插入“张三”方法和外围方法一个事务，
	// 插入“李四”方法、插入“王五”方法分别在独立的新建事务中。
	// 插入“王五”方法抛出异常，首先插入 “王五”方法的事务被回滚，
	// 异常继续抛出被外围方法感知，外围方法事务亦被回滚，故插入“张三”方法也被回滚。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_requiresNew_requiresNew_exception() {
		user1Service.required("张三");

		user2Service.requires_new("李四");

		user2Service.requires_new_exception("王五");
	}

	// 外围方法开启事务，插入“张三”方法和外围方法一个事务，
	// 插入“李四”方法、插入“王五”方法分别在独立的新建事务中。
	// 插入“王五”方法抛出异常，首先插入“王五”方法的事务被回滚，
	// 异常被catch不会被外围方法感知，外围方法事务不回滚，故插入“张三”方法插入成功。
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_requiresNew_requiresNew_exception_try(){
		user1Service.required("张三");

		user2Service.requires_new("李四");

		try {
			user2Service.requires_new_exception("王五");
		} catch (Exception e) {
			System.out.println("回滚");
		}
	}

	/**
	 * ==============================================================
	 * 	场景3.1：外围方法没有事务,内部调用两个NESTED方法
	 * ==============================================================
	 */

	// 外围方法未开启事务，
	// 插入“张三”、“李四”方法在自己的事务中独立运行，
	// 外围方法异常不影响内部插入“张三”、“李四”方法独立的事务。
	public void notransaction_exception_nested_nested(){
		user1Service.nested("张三");
		user2Service.nested("李四");
		throw new RuntimeException();
	}

	// 外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行,
	// 所以插入“李四”方法抛出异常只会回滚插入“李四”方法，插入“张三”方法不受影响。
	public void notransaction_nested_nested_exception(){
		user1Service.nested("张三");
		user2Service.nested_exception("李四");
	}

	/**
	 * ==============================================================
	 * 	场景3.2：外围方法有事务,内部调用两个NESTED方法
	 * ==============================================================
	 */

	// 外围方法开启事务，内部事务为外围事务的子事务，外围方法回滚，内部方法也要回滚。
	@Transactional
	public void transaction_exception_nested_nested(){
		user1Service.nested("张三");
		user2Service.nested("李四");
		throw new RuntimeException();
	}

	// 外围方法开启事务，内部事务为外围事务的子事务，
	// 内部方法抛出异常回滚，且外围方法感知异常致使整体事务回滚。
	@Transactional
	public void transaction_nested_nested_exception(){
		user1Service.nested("张三");
		user2Service.nested_exception("李四");
	}

	// 外围方法开启事务，内部事务为外围事务的子事务，
	// 插入“李四”内部方法抛出异常，可以单独对子事务回滚。
	// 外围事务没有感知异常，事务提交
	@Transactional
	public void transaction_nested_nested_exception_try(){
		user1Service.nested("张三");
		try {
			user2Service.nested_exception("李四");
		} catch (Exception e) {
			System.out.println("方法回滚");
		}
	}


}
