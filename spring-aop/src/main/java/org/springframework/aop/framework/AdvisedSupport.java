/*
 * Copyright 2002-2024 the original author or authors.
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

package org.springframework.aop.framework;

import org.aopalliance.aop.Advice;
import org.springframework.aop.*;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.target.EmptyTargetSource;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for AOP proxy configuration managers.
 *
 * <p>These are not themselves AOP proxies, but subclasses of this class are
 * normally factories from which AOP proxy instances are obtained directly.
 *
 * <p>This class frees subclasses of the housekeeping of Advices
 * and Advisors, but doesn't actually implement proxy creation
 * methods, which are provided by subclasses.
 *
 * <p>This class is serializable; subclasses need not be.
 *
 * <p>This class is used to hold snapshots of proxies.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @see org.springframework.aop.framework.AopProxy
 */
public class AdvisedSupport extends ProxyConfig implements Advised {

	/** use serialVersionUID from Spring 2.0 for interoperability. */
	private static final long serialVersionUID = 2651364800145442165L;


	/**
	 * Canonical TargetSource when there's no target, and behavior is
	 * supplied by the advisors.
	 */
	public static final TargetSource EMPTY_TARGET_SOURCE = EmptyTargetSource.INSTANCE;


	// 目标源，默认是EMPTY_TARGET_SOURCE
	/** Package-protected to allow direct access for efficiency. */
	TargetSource targetSource = EMPTY_TARGET_SOURCE;

	/** Whether the Advisors are already filtered for the specific target class. */
	// 是否做过预处理
	private boolean preFiltered = false;

	/** The AdvisorChainFactory to use. */
	// 调用链工厂，获取目标方法的调用链
	private AdvisorChainFactory advisorChainFactory = DefaultAdvisorChainFactory.INSTANCE;

	/**
	 * Interfaces to be implemented by the proxy. Held in List to keep the order
	 * of registration, to create JDK proxy with specified order of interfaces.
	 */
	// 代理对象需要实现的接口列表
	private List<Class<?>> interfaces = new ArrayList<>();

	/**
	 * List of Advisors. If an Advice is added, it will be wrapped
	 * in an Advisor before being added to this List.
	 */
	// 存储顾问，添加的Advice会被包装成Advisor对象， DefaultPointcutAdvisor
	private List<Advisor> advisors = new ArrayList<>();

	/**
	 * List of minimal {@link AdvisorKeyEntry} instances,
	 * to be assigned to the {@link #advisors} field on reduction.
	 * @since 6.0.10
	 * @see #reduceToAdvisorKey
	 */
	private List<Advisor> advisorKey = this.advisors;

	/** Cache with Method as key and advisor chain List as value. */
	// 以方法为键，以顾问链表为值的缓存
	@Nullable
	private transient Map<MethodCacheKey, List<Object>> methodCache;

	/** Cache with shared interceptors which are not method-specific. */
	// 缓存的顾问
	@Nullable
	private transient volatile List<Object> cachedInterceptors;

	/**
	 * Optional field for {@link AopProxy} implementations to store metadata in.
	 * Used by {@link JdkDynamicAopProxy}.
	 * @since 6.1.3
	 * @see JdkDynamicAopProxy#JdkDynamicAopProxy(AdvisedSupport)
	 */
	@Nullable
	transient volatile Object proxyMetadataCache;


	/**
	 * No-arg constructor for use as a JavaBean.
	 */
	public AdvisedSupport() {
	}

	/**
	 * Create an {@code AdvisedSupport} instance with the given parameters.
	 * @param interfaces the proxied interfaces
	 */
	public AdvisedSupport(Class<?>... interfaces) {
		setInterfaces(interfaces);
	}


	/**
	 * Set the given object as target.
	 * <p>Will create a SingletonTargetSource for the object.
	 * @see #setTargetSource
	 * @see org.springframework.aop.target.SingletonTargetSource
	 */
	public void setTarget(Object target) {
		setTargetSource(new SingletonTargetSource(target));
	}

	@Override
	public void setTargetSource(@Nullable TargetSource targetSource) {
		this.targetSource = (targetSource != null ? targetSource : EMPTY_TARGET_SOURCE);
	}

	@Override
	public TargetSource getTargetSource() {
		return this.targetSource;
	}

	/**
	 * 设置目标类
	 * Set a target class to be proxied, indicating that the proxy
	 * should be castable to the given class.
	 * <p>Internally, an {@link org.springframework.aop.target.EmptyTargetSource}
	 * for the given target class will be used. The kind of proxy needed
	 * will be determined on actual creation of the proxy.
	 * <p>This is a replacement for setting a "targetSource" or "target",
	 * for the case where we want a proxy based on a target class
	 * (which can be an interface or a concrete class) without having
	 * a fully capable TargetSource available.
	 * @see #setTargetSource
	 * @see #setTarget
	 */
	public void setTargetClass(@Nullable Class<?> targetClass) {
		this.targetSource = EmptyTargetSource.forClass(targetClass);
	}

	@Override
	@Nullable
	public Class<?> getTargetClass() {
		return this.targetSource.getTargetClass();
	}

	@Override
	public void setPreFiltered(boolean preFiltered) {
		this.preFiltered = preFiltered;
	}

	@Override
	public boolean isPreFiltered() {
		return this.preFiltered;
	}

	/**
	 * Set the advisor chain factory to use.
	 * <p>Default is a {@link DefaultAdvisorChainFactory}.
	 */
	public void setAdvisorChainFactory(AdvisorChainFactory advisorChainFactory) {
		Assert.notNull(advisorChainFactory, "AdvisorChainFactory must not be null");
		this.advisorChainFactory = advisorChainFactory;
	}

	/**
	 * Return the advisor chain factory to use (never {@code null}).
	 */
	public AdvisorChainFactory getAdvisorChainFactory() {
		return this.advisorChainFactory;
	}


	/**
	 * Set the interfaces to be proxied.
	 */
	public void setInterfaces(Class<?>... interfaces) {
		Assert.notNull(interfaces, "Interfaces must not be null");
		this.interfaces.clear();
		for (Class<?> ifc : interfaces) {
			addInterface(ifc);
		}
	}

	/**
	 * 添加需要实现的接口
	 * Add a new proxied interface.
	 * @param ifc the additional interface to proxy
	 */
	public void addInterface(Class<?> ifc) {
		Assert.notNull(ifc, "Interface must not be null");
		if (!ifc.isInterface()) {
			throw new IllegalArgumentException("[" + ifc.getName() + "] is not an interface");
		}
		if (!this.interfaces.contains(ifc)) {
			this.interfaces.add(ifc);
			// 清除缓存
			adviceChanged();
		}
	}

	/**
	 * 移除接口
	 * Remove a proxied interface.
	 * <p>Does nothing if the given interface isn't proxied.
	 * @param ifc the interface to remove from the proxy
	 * @return {@code true} if the interface was removed; {@code false}
	 * if the interface was not found and hence could not be removed
	 */
	public boolean removeInterface(Class<?> ifc) {
		return this.interfaces.remove(ifc);
	}

	@Override
	public Class<?>[] getProxiedInterfaces() {
		return ClassUtils.toClassArray(this.interfaces);
	}

	// 判断接口是否需要被代理
	@Override
	public boolean isInterfaceProxied(Class<?> ifc) {
		for (Class<?> proxyIntf : this.interfaces) {
			if (ifc.isAssignableFrom(proxyIntf)) {
				return true;
			}
		}
		return false;
	}

	// 是否有用户接口
	boolean hasUserSuppliedInterfaces() {
		for (Class<?> ifc : this.interfaces) {
			// 不是SpringProxy && 不是IntroductionAdvisor引入的接口
			if (!SpringProxy.class.isAssignableFrom(ifc) && !isAdvisorIntroducedInterface(ifc)) {
				return true;
			}
		}
		return false;
	}

	// 是否有IntroductionAdvisor引入的接口
	private boolean isAdvisorIntroducedInterface(Class<?> ifc) {
		for (Advisor advisor : this.advisors) {
			if (advisor instanceof IntroductionAdvisor introductionAdvisor) {
				for (Class<?> introducedInterface : introductionAdvisor.getInterfaces()) {
					if (introducedInterface == ifc) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// 获取顾问数组
	@Override
	public final Advisor[] getAdvisors() {
		return this.advisors.toArray(new Advisor[0]);
	}

	@Override
	public int getAdvisorCount() {
		return this.advisors.size();
	}

	// 添加顾问到最后
	@Override
	public void addAdvisor(Advisor advisor) {
		int pos = this.advisors.size();
		addAdvisor(pos, advisor);
	}

	// 添加顾问到指定位置
	@Override
	public void addAdvisor(int pos, Advisor advisor) throws AopConfigException {
		if (advisor instanceof IntroductionAdvisor introductionAdvisor) {
			validateIntroductionAdvisor(introductionAdvisor);
		}
		// 内部方法添加顾问到指定位置
		addAdvisorInternal(pos, advisor);
	}

	// 移除指定的顾问
	@Override
	public boolean removeAdvisor(Advisor advisor) {
		int index = indexOf(advisor);
		if (index == -1) {
			return false;
		}
		else {
			removeAdvisor(index);
			return true;
		}
	}

	//
	@Override
	public void removeAdvisor(int index) throws AopConfigException {
		// 被冻结的配置不允许修改顾问集合
		if (isFrozen()) {
			throw new AopConfigException("Cannot remove Advisor: Configuration is frozen.");
		}
		// 检查下标是否越界
		if (index < 0 || index > this.advisors.size() - 1) {
			throw new AopConfigException("Advisor index " + index + " is out of bounds: " +
					"This configuration only has " + this.advisors.size() + " advisors.");
		}

		// 移除指定的顾问
		Advisor advisor = this.advisors.remove(index);
		if (advisor instanceof IntroductionAdvisor introductionAdvisor) {
			// We need to remove introduction interfaces.
			for (Class<?> ifc : introductionAdvisor.getInterfaces()) {
				// 移除 IntroductionAdvisor引入的接口
				removeInterface(ifc);
			}
		}

		// 清除缓存
		adviceChanged();
	}

	@Override
	public int indexOf(Advisor advisor) {
		Assert.notNull(advisor, "Advisor must not be null");
		return this.advisors.indexOf(advisor);
	}

	// 替换顾问
	@Override
	public boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException {
		Assert.notNull(a, "Advisor a must not be null");
		Assert.notNull(b, "Advisor b must not be null");
		int index = indexOf(a);
		if (index == -1) {
			return false;
		}
		removeAdvisor(index);
		addAdvisor(index, b);
		return true;
	}

	/**
	 * 批量添加顾问
	 * Add all the given advisors to this proxy configuration.
	 * @param advisors the advisors to register
	 */
	public void addAdvisors(Advisor... advisors) {
		addAdvisors(Arrays.asList(advisors));
	}

	/**
	 * 批量添加顾问
	 * Add all the given advisors to this proxy configuration.
	 * @param advisors the advisors to register
	 */
	public void addAdvisors(Collection<Advisor> advisors) {
		// 配置被冻结时，抛出异常
		if (isFrozen()) {
			throw new AopConfigException("Cannot add advisor: Configuration is frozen.");
		}
		if (!CollectionUtils.isEmpty(advisors)) {
			// 遍历所有顾问，添加到顾问集合中
			for (Advisor advisor : advisors) {
				if (advisor instanceof IntroductionAdvisor introductionAdvisor) {
					validateIntroductionAdvisor(introductionAdvisor);
				}
				Assert.notNull(advisor, "Advisor must not be null");
				this.advisors.add(advisor);
			}
			// 清除缓存
			adviceChanged();
		}
	}

	private void validateIntroductionAdvisor(IntroductionAdvisor advisor) {
		advisor.validateInterfaces();
		// If the advisor passed validation, we can make the change.
		// IntroductionAdvisor引入接口
		for (Class<?> ifc : advisor.getInterfaces()) {
			addInterface(ifc);
		}
	}

	private void addAdvisorInternal(int pos, Advisor advisor) throws AopConfigException {
		Assert.notNull(advisor, "Advisor must not be null");
		// 配置被冻结时修改会抛出异常
		if (isFrozen()) {
			throw new AopConfigException("Cannot add advisor: Configuration is frozen.");
		}
		// 检查下标是否越界
		if (pos > this.advisors.size()) {
			throw new IllegalArgumentException(
					"Illegal position " + pos + " in advisor list with size " + this.advisors.size());
		}
		// 添加顾问
		this.advisors.add(pos, advisor);
		// 清除缓存
		adviceChanged();
	}

	/**
	 * Allows uncontrolled access to the {@link List} of {@link Advisor Advisors}.
	 * <p>Use with care, and remember to {@link #adviceChanged() fire advice changed events}
	 * when making any modifications.
	 */
	protected final List<Advisor> getAdvisorsInternal() {
		return this.advisors;
	}

	// 添加通知
	@Override
	public void addAdvice(Advice advice) throws AopConfigException {
		int pos = this.advisors.size();
		addAdvice(pos, advice);
	}

	/**
	 * 默认将advice包装成DefaultPointcutAdvisor
	 * Cannot add introductions this way unless the advice implements IntroductionInfo.
	 */
	@Override
	public void addAdvice(int pos, Advice advice) throws AopConfigException {
		Assert.notNull(advice, "Advice must not be null");
		if (advice instanceof IntroductionInfo introductionInfo) {
			// We don't need an IntroductionAdvisor for this kind of introduction:
			// It's fully self-describing.
			addAdvisor(pos, new DefaultIntroductionAdvisor(advice, introductionInfo));
		}
		else if (advice instanceof DynamicIntroductionAdvice) {
			// We need an IntroductionAdvisor for this kind of introduction.
			throw new AopConfigException("DynamicIntroductionAdvice may only be added as part of IntroductionAdvisor");
		}
		else {
			// 默认将advice包装成DefaultPointcutAdvisor
			addAdvisor(pos, new DefaultPointcutAdvisor(advice));
		}
	}

	@Override
	public boolean removeAdvice(Advice advice) throws AopConfigException {
		int index = indexOf(advice);
		if (index == -1) {
			return false;
		}
		else {
			removeAdvisor(index);
			return true;
		}
	}

	@Override
	public int indexOf(Advice advice) {
		Assert.notNull(advice, "Advice must not be null");
		for (int i = 0; i < this.advisors.size(); i++) {
			Advisor advisor = this.advisors.get(i);
			if (advisor.getAdvice() == advice) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 是否包含指定通知
	 * Is the given advice included in any advisor within this proxy configuration?
	 * @param advice the advice to check inclusion of
	 * @return whether this advice instance is included
	 */
	public boolean adviceIncluded(@Nullable Advice advice) {
		if (advice != null) {
			for (Advisor advisor : this.advisors) {
				if (advisor.getAdvice() == advice) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 统计指定类型的通知数量
	 * Count advices of the given class.
	 * @param adviceClass the advice class to check
	 * @return the count of the interceptors of this class or subclasses
	 */
	public int countAdvicesOfType(@Nullable Class<?> adviceClass) {
		int count = 0;
		if (adviceClass != null) {
			for (Advisor advisor : this.advisors) {
				if (adviceClass.isInstance(advisor.getAdvice())) {
					count++;
				}
			}
		}
		return count;
	}


	/**
	 * 调用代理的方法时会执行
	 * Determine a list of {@link org.aopalliance.intercept.MethodInterceptor} objects
	 * for the given method, based on this configuration.
	 * @param method the proxied method
	 * @param targetClass the target class
	 * @return a List of MethodInterceptors (may also include InterceptorAndDynamicMethodMatchers)
	 */
	//基于当前配置，获取给定方法的方法调用链列表 ，内部委派给DefaultAdvisorChainFactory
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) {
		List<Object> cachedInterceptors;
		if (this.methodCache != null) {
			// Method-specific cache for method-specific pointcuts
			MethodCacheKey cacheKey = new MethodCacheKey(method);
			// 从缓存中获取
			cachedInterceptors = this.methodCache.get(cacheKey);
			if (cachedInterceptors == null) {
				// 通过工厂获取
				cachedInterceptors = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
						this, method, targetClass);
				// 放入缓存中
				this.methodCache.put(cacheKey, cachedInterceptors);
			}
		}
		else {
			// Shared cache since there are no method-specific advisors (see below).
			// 获取缓存的顾问
			cachedInterceptors = this.cachedInterceptors;
			if (cachedInterceptors == null) {
				// 通过工厂获取
				cachedInterceptors = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
						this, method, targetClass);
				// 放入缓存的顾问列表
				this.cachedInterceptors = cachedInterceptors;
			}
		}
		return cachedInterceptors;
	}

	/**
	 * Invoked when advice has changed.
	 */
	protected void adviceChanged() {
		this.methodCache = null;
		this.cachedInterceptors = null;
		this.proxyMetadataCache = null;

		// Initialize method cache if necessary; otherwise,
		// cachedInterceptors is going to be shared (see above).
		for (Advisor advisor : this.advisors) {
			if (advisor instanceof PointcutAdvisor) {
				this.methodCache = new ConcurrentHashMap<>();
				break;
			}
		}
	}

	/**
	 * Call this method on a new instance created by the no-arg constructor
	 * to create an independent copy of the configuration from the given object.
	 * @param other the AdvisedSupport object to copy configuration from
	 */
	protected void copyConfigurationFrom(AdvisedSupport other) {
		copyConfigurationFrom(other, other.targetSource, new ArrayList<>(other.advisors));
	}

	/**
	 * Copy the AOP configuration from the given {@link AdvisedSupport} object,
	 * but allow substitution of a fresh {@link TargetSource} and a given interceptor chain.
	 * @param other the {@code AdvisedSupport} object to take proxy configuration from
	 * @param targetSource the new TargetSource
	 * @param advisors the Advisors for the chain
	 */
	protected void copyConfigurationFrom(AdvisedSupport other, TargetSource targetSource, List<Advisor> advisors) {
		copyFrom(other);
		this.targetSource = targetSource;
		this.advisorChainFactory = other.advisorChainFactory;
		this.interfaces = new ArrayList<>(other.interfaces);
		for (Advisor advisor : advisors) {
			if (advisor instanceof IntroductionAdvisor introductionAdvisor) {
				validateIntroductionAdvisor(introductionAdvisor);
			}
			Assert.notNull(advisor, "Advisor must not be null");
			this.advisors.add(advisor);
		}
		adviceChanged();
	}

	/**
	 * Build a configuration-only copy of this {@link AdvisedSupport},
	 * replacing the {@link TargetSource}.
	 */
	AdvisedSupport getConfigurationOnlyCopy() {
		AdvisedSupport copy = new AdvisedSupport();
		copy.copyFrom(this);
		copy.targetSource = EmptyTargetSource.forClass(getTargetClass(), getTargetSource().isStatic());
		copy.preFiltered = this.preFiltered;
		copy.advisorChainFactory = this.advisorChainFactory;
		copy.interfaces = new ArrayList<>(this.interfaces);
		copy.advisors = new ArrayList<>(this.advisors);
		copy.advisorKey = new ArrayList<>(this.advisors.size());
		for (Advisor advisor : this.advisors) {
			copy.advisorKey.add(new AdvisorKeyEntry(advisor));
		}
		copy.methodCache = this.methodCache;
		copy.cachedInterceptors = this.cachedInterceptors;
		copy.proxyMetadataCache = this.proxyMetadataCache;
		return copy;
	}

	void reduceToAdvisorKey() {
		this.advisors = this.advisorKey;
		this.methodCache = null;
		this.cachedInterceptors = null;
		this.proxyMetadataCache = null;
	}

	Object getAdvisorKey() {
		return this.advisorKey;
	}


	@Override
	public String toProxyConfigString() {
		return toString();
	}

	/**
	 * For debugging/diagnostic use.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append(": ").append(this.interfaces.size()).append(" interfaces ");
		sb.append(ClassUtils.classNamesToString(this.interfaces)).append("; ");
		sb.append(this.advisors.size()).append(" advisors ");
		sb.append(this.advisors).append("; ");
		sb.append("targetSource [").append(this.targetSource).append("]; ");
		sb.append(super.toString());
		return sb.toString();
	}


	//---------------------------------------------------------------------
	// Serialization support
	//---------------------------------------------------------------------

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		// Rely on default serialization; just initialize state after deserialization.
		ois.defaultReadObject();

		// Initialize method cache if necessary.
		adviceChanged();
	}


	/**
	 * Simple wrapper class around a Method. Used as the key when
	 * caching methods, for efficient equals and hashCode comparisons.
	 */
	private static final class MethodCacheKey implements Comparable<MethodCacheKey> {

		private final Method method;

		private final int hashCode;

		public MethodCacheKey(Method method) {
			this.method = method;
			this.hashCode = method.hashCode();
		}

		@Override
		public boolean equals(@Nullable Object other) {
			return (this == other || (other instanceof MethodCacheKey that &&
					(this.method == that.method || this.method.equals(that.method))));
		}

		@Override
		public int hashCode() {
			return this.hashCode;
		}

		@Override
		public String toString() {
			return this.method.toString();
		}

		@Override
		public int compareTo(MethodCacheKey other) {
			int result = this.method.getName().compareTo(other.method.getName());
			if (result == 0) {
				result = this.method.toString().compareTo(other.method.toString());
			}
			return result;
		}
	}


	/**
	 * Stub for an {@link Advisor} instance that is just needed for key purposes,
	 * allowing for efficient equals and hashCode comparisons against the
	 * advice class and the pointcut.
	 * @since 6.0.10
	 * @see #getConfigurationOnlyCopy()
	 * @see #getAdvisorKey()
	 */
	private static final class AdvisorKeyEntry implements Advisor {

		private final Class<?> adviceType;

		@Nullable
		private final String classFilterKey;

		@Nullable
		private final String methodMatcherKey;

		public AdvisorKeyEntry(Advisor advisor) {
			this.adviceType = advisor.getAdvice().getClass();
			if (advisor instanceof PointcutAdvisor pointcutAdvisor) {
				Pointcut pointcut = pointcutAdvisor.getPointcut();
				this.classFilterKey = pointcut.getClassFilter().toString();
				this.methodMatcherKey = pointcut.getMethodMatcher().toString();
			}
			else {
				this.classFilterKey = null;
				this.methodMatcherKey = null;
			}
		}

		@Override
		public Advice getAdvice() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean equals(Object other) {
			return (this == other || (other instanceof AdvisorKeyEntry that &&
					this.adviceType == that.adviceType &&
					ObjectUtils.nullSafeEquals(this.classFilterKey, that.classFilterKey) &&
					ObjectUtils.nullSafeEquals(this.methodMatcherKey, that.methodMatcherKey)));
		}

		@Override
		public int hashCode() {
			return this.adviceType.hashCode();
		}
	}

}
