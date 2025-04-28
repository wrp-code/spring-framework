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
public class Tx1Service {
	@Autowired
	private Ds1User1Service ds1User1Service;
	@Autowired
	private Ds1User2Service ds1User2Service;
	@Autowired
	private Ds2User1Service ds2User1Service;
	@Autowired
	private Ds2User2Service ds2User2Service;

	@Autowired
	private Tx2Service tx2Service;

	/**
	 * ==============================================================
	 * 	场景1：外围方法和内部方法使用相同的事务管理器，传播行为都是 REQUIRED。
	 * ==============================================================
	 */

	// 外围方法和内部方法使用同一个事务管理器 transactionManager1，且事务管理器和 jdbctemplate 的 datasource 都是同一个，
	// 外围方法会开启事务，内部方法加入外围方法事务，外围方法弹出异常导致事务回滚，内部方法跟着回滚了。
	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void test1() {
		this.ds1User1Service.required("张三");
		this.ds1User2Service.required("李四");
		throw new RuntimeException();
	}

	/**
	 * ==============================================================
	 * 	场景2：外围方法和内部方法使用不同的事务管理器，传播行为都是 REQUIRED。
	 * ==============================================================
	 */

	//外围方法 test2 和内部两个 required 方法用到的不是同一个事务管理器，内部的 2 个方法在各自的事务中执行，不受外部方法事务的控制。
	@Transactional(transactionManager = "transactionManager2", propagation = Propagation.REQUIRED)
	public void test2() {
		this.ds1User1Service.required("张三");
		this.ds1User2Service.required("李四");
		throw new RuntimeException();
	}

	/**
	 * ==============================================================
	 * 	场景3：外围方法和部分内部方法使用相同的事务管理器，传播行为都是 REQUIRED。
	 * ==============================================================
	 */

	// 外围方法和内部的前 2 个 required 方法事务管理器都是 transactionManager1，所以他们 3 个在一个事务中执行；
	// 而内部的后 2 个 required 方法事务管理器是 transactionManager2，他们分别在自己的事务中执行，不受外围方法事务的控制，
	// 外围方法感受到了异常，回滚事务，只会导致内部的前 2 个 required 方法回滚。
	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void test3() {
		this.ds1User1Service.required("张三");
		this.ds1User2Service.required("李四");
		this.ds2User1Service.required("王五");
		this.ds2User2Service.required("赵六");
		throw new RuntimeException();
	}

	/**
	 * ==============================================================
	 * 	场景4：外围方法和部分内部方法使用相同的事务管理器，传播行为都是 REQUIRED。
	 * ==============================================================
	 */

	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void test4() {
		this.ds1User1Service.required("张三");
		this.ds1User2Service.required("李四");
		this.tx2Service.test1();
		throw new RuntimeException();
	}

	/**
	 * ==============================================================
	 * 	场景5：外围方法和部分内部方法使用相同的事务管理器，传播行为都是 REQUIRED。
	 * ==============================================================
	 */

	// test2 内部抛出了异常，外围方法感知到异常同样回滚
	@Transactional(transactionManager = "transactionManager1", propagation = Propagation.REQUIRED)
	public void test5() {
		this.ds1User1Service.required("张三");
		this.ds1User2Service.required("李四");
		this.tx2Service.test2();
	}
}