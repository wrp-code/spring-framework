/*
 * Copyright 2002-2023 the original author or authors.
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

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.*;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple but definitive way of working out an advice chain for a Method,
 * given an {@link Advised} object. Always rebuilds each advice chain;
 * caching can be provided by subclasses.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Adrian Colyer
 * @since 2.0.3
 */
@SuppressWarnings("serial")
public class DefaultAdvisorChainFactory implements AdvisorChainFactory, Serializable {
	// 需要用到AdvisorAdapterRegistry
	// AdvisorAdapter 顾问适配器

	/**
	 * Singleton instance of this class.
	 * @since 6.0.10
	 */
	public static final DefaultAdvisorChainFactory INSTANCE = new DefaultAdvisorChainFactory();


	// 通过方法和目标类的类型，从当前配置中会获取匹配的方法拦截器列表
	@Override
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(
			Advised config, Method method, @Nullable Class<?> targetClass) {

		// This is somewhat tricky... We have to process introductions first,
		// but we need to preserve order in the ultimate list.
		// 获取顾问适配器注册器
		AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
		// 获取所有的顾问集合
		Advisor[] advisors = config.getAdvisors();
		// 拦截器链
		List<Object> interceptorList = new ArrayList<>(advisors.length);
		// 获取目标类
		Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
		Boolean hasIntroductions = null;

		// 遍历顾问数组，找到和actualClass和方法匹配的所有方法拦截器（MethodInterceptor）链列表
		for (Advisor advisor : advisors) {
			// 如果是PointcutAdvisor
			if (advisor instanceof PointcutAdvisor pointcutAdvisor) {
				// Add it conditionally.
				// 如果是预过滤处理 || 类匹配
				if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
					// 获取方法匹配器
					MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
					// 匹配结果
					boolean match;
					if (mm instanceof IntroductionAwareMethodMatcher iamm) {
						if (hasIntroductions == null) {
							// 是否有匹配的IntroductionAdvisor
							hasIntroductions = hasMatchingIntroductions(advisors, actualClass);
						}
						match = iamm.matches(method, actualClass, hasIntroductions);
					}
					else {
						// 方法匹配
						match = mm.matches(method, actualClass);
					}
					// 如果首次匹配上
					if (match) {
						// 从Registry中获取顾问相关的拦截器
						MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
						// 如果是动态方法匹配器
						if (mm.isRuntime()) {
							// Creating a new object instance in the getInterceptors() method
							// isn't a problem as we normally cache created chains.
							for (MethodInterceptor interceptor : interceptors) {
								// 将拦截器包装为动态方法匹配拦截器InterceptorAndDynamicMethodMatcher
								interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
							}
						}
						else {
							// 如果不是动态方法匹配器，直接添加到拦截器列表中
							interceptorList.addAll(Arrays.asList(interceptors));
						}
					}
				}
			}
			// 如果是IntroductionAdvisor
			else if (advisor instanceof IntroductionAdvisor ia) {
				// 预处理 || 类型匹配
				if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
					// 从Registry中获取拦截器，添加到
					Interceptor[] interceptors = registry.getInterceptors(advisor);
					interceptorList.addAll(Arrays.asList(interceptors));
				}
			}
			else {
				Interceptor[] interceptors = registry.getInterceptors(advisor);
				interceptorList.addAll(Arrays.asList(interceptors));
			}
		}

		return interceptorList;
	}

	/**
	 * 是否有匹配的IntroductionAdvisor
	 * Determine whether the Advisors contain matching introductions.
	 */
	private static boolean hasMatchingIntroductions(Advisor[] advisors, Class<?> actualClass) {
		for (Advisor advisor : advisors) {
			if (advisor instanceof IntroductionAdvisor ia && ia.getClassFilter().matches(actualClass)) {
				return true;
			}
		}
		return false;
	}

}
