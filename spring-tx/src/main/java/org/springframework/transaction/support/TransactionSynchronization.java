/*
 * Copyright 2002-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction.support;

import org.springframework.core.Ordered;

import java.io.Flushable;

/**
 * Interface for transaction synchronization callbacks.
 * Supported by AbstractPlatformTransactionManager.
 *
 * <p>TransactionSynchronization implementations can implement the Ordered interface
 * to influence their execution order. A synchronization that does not implement the
 * Ordered interface is appended to the end of the synchronization chain.
 *
 * <p>System synchronizations performed by Spring itself use specific order values,
 * allowing for fine-grained interaction with their execution order (if necessary).
 *
 * <p>Implements the {@link Ordered} interface to enable the execution order of
 * synchronizations to be controlled declaratively. The default {@link #getOrder()
 * order} is {@link Ordered#LOWEST_PRECEDENCE}, indicating late execution; return
 * a lower value for earlier execution.
 *
 * @author Juergen Hoeller
 * @since 02.06.2003
 * @see TransactionSynchronizationManager
 * @see AbstractPlatformTransactionManager
 * @see org.springframework.jdbc.datasource.DataSourceUtils#CONNECTION_SYNCHRONIZATION_ORDER
 */
// 事务执行过程中的回调接口
// 事务提交之前、提交之后、回滚之前、回滚之后做一些事务
public interface TransactionSynchronization extends Ordered, Flushable {

	/** 提交状态 */
	int STATUS_COMMITTED = 0;

	/** 回滚状态 */
	int STATUS_ROLLED_BACK = 1;

	/**状态未知，比如事务提交或者回滚的过程中发生了异常，那么事务的状态是未知的*/
	int STATUS_UNKNOWN = 2;


	/**
	 * Return the execution order for this transaction synchronization.
	 * <p>Default is {@link Ordered#LOWEST_PRECEDENCE}.
	 */
	@Override
	default int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	/**
	 * Suspend this synchronization.
	 * Supposed to unbind resources from TransactionSynchronizationManager if managing any.
	 * @see TransactionSynchronizationManager#unbindResource
	 */
	default void suspend() {
		//事务被挂起的时候会调用被挂起事务中所有TransactionSynchronization的resume方法
	}

	/**
	 * Resume this synchronization.
	 * Supposed to rebind resources to TransactionSynchronizationManager if managing any.
	 * @see TransactionSynchronizationManager#bindResource
	 */
	default void resume() {
		//事务恢复的过程中会调用被恢复的事务中所有TransactionSynchronization的resume方法
	}

	/**
	 * Flush the underlying session to the datastore, if applicable:
	 * for example, a Hibernate/JPA session.
	 * @see org.springframework.transaction.TransactionStatus#flush()
	 */
	@Override
	default void flush() {
		//清理操作
	}

	/**
	 * Invoked on creation of a new savepoint, either when a nested transaction
	 * is started against an existing transaction or on a programmatic savepoint
	 * via {@link org.springframework.transaction.TransactionStatus}.
	 * <p>This synchronization callback is invoked right <i>after</i> the creation
	 * of the resource savepoint, with the given savepoint object already active.
	 * @param savepoint the associated savepoint object (primarily as a key for
	 * identifying the savepoint but also castable to the resource savepoint type)
	 * @since 6.2
	 * @see org.springframework.transaction.SavepointManager#createSavepoint
	 * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_NESTED
	 */
	default void savepoint(Object savepoint) {
	}

	/**
	 * Invoked in case of a rollback to the previously created savepoint.
	 * <p>This synchronization callback is invoked right <i>before</i> the rollback
	 * of the resource savepoint, with the given savepoint object still active.
	 * @param savepoint the associated savepoint object (primarily as a key for
	 * identifying the savepoint but also castable to the resource savepoint type)
	 * @since 6.2
	 * @see #savepoint
	 * @see org.springframework.transaction.SavepointManager#rollbackToSavepoint
	 */
	default void savepointRollback(Object savepoint) {
	}

	/**
	 * Invoked before transaction commit (before "beforeCompletion").
	 * Can, for example, flush transactional O/R Mapping sessions to the database.
	 * <p>This callback does <i>not</i> mean that the transaction will actually be committed.
	 * A rollback decision can still occur after this method has been called. This callback
	 * is rather meant to perform work that's only relevant if a commit still has a chance
	 * to happen, such as flushing SQL statements to the database.
	 * <p>Note that exceptions will get propagated to the commit caller and cause a
	 * rollback of the transaction.
	 * @param readOnly whether the transaction is defined as read-only transaction
	 * @throws RuntimeException in case of errors; will be <b>propagated to the caller</b>
	 * (note: do not throw TransactionException subclasses here!)
	 * @see #beforeCompletion
	 */
	default void beforeCommit(boolean readOnly) {
		//事务提交之前调用
	}

	/**
	 * Invoked before transaction commit/rollback.
	 * Can perform resource cleanup <i>before</i> transaction completion.
	 * <p>This method will be invoked after {@code beforeCommit}, even when
	 * {@code beforeCommit} threw an exception. This callback allows for
	 * closing resources before transaction completion, for any outcome.
	 * @throws RuntimeException in case of errors; will be <b>logged but not propagated</b>
	 * (note: do not throw TransactionException subclasses here!)
	 * @see #beforeCommit
	 * @see #afterCompletion
	 */
	default void beforeCompletion() {
		//事务提交或者回滚之前调用
	}

	/**
	 * Invoked after transaction commit. Can perform further operations right
	 * <i>after</i> the main transaction has <i>successfully</i> committed.
	 * <p>Can, for example, commit further operations that are supposed to follow on a successful
	 * commit of the main transaction, like confirmation messages or emails.
	 * <p><b>NOTE:</b> The transaction will have been committed already, but the
	 * transactional resources might still be active and accessible. As a consequence,
	 * any data access code triggered at this point will still "participate" in the
	 * original transaction, allowing to perform some cleanup (with no commit following
	 * anymore!), unless it explicitly declares that it needs to run in a separate
	 * transaction. Hence: <b>Use {@code PROPAGATION_REQUIRES_NEW} for any
	 * transactional operation that is called from here.</b>
	 * @throws RuntimeException in case of errors; will be <b>propagated to the caller</b>
	 * (note: do not throw TransactionException subclasses here!)
	 */
	default void afterCommit() {
		//事务commit之后调用
	}

	/**
	 * Invoked after transaction commit/rollback.
	 * Can perform resource cleanup <i>after</i> transaction completion.
	 * <p><b>NOTE:</b> The transaction will have been committed or rolled back already,
	 * but the transactional resources might still be active and accessible. As a
	 * consequence, any data access code triggered at this point will still "participate"
	 * in the original transaction, allowing to perform some cleanup (with no commit
	 * following anymore!), unless it explicitly declares that it needs to run in a
	 * separate transaction. Hence: <b>Use {@code PROPAGATION_REQUIRES_NEW}
	 * for any transactional operation that is called from here.</b>
	 * @param status completion status according to the {@code STATUS_*} constants
	 * @throws RuntimeException in case of errors; will be <b>logged but not propagated</b>
	 * (note: do not throw TransactionException subclasses here!)
	 * @see #STATUS_COMMITTED
	 * @see #STATUS_ROLLED_BACK
	 * @see #STATUS_UNKNOWN
	 * @see #beforeCompletion
	 */
	default void afterCompletion(int status) {
		//事务完成之后调用
	}

}
