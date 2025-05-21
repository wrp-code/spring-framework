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

package org.springframework.aop.framework.adapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.AfterAdvice;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Interceptor to wrap an after-throwing advice.
 *
 * <p>The signatures on handler methods on the {@code ThrowsAdvice}
 * implementation method argument must be of the form:<br>
 *
 * {@code void afterThrowing([Method, args, target], ThrowableSubclass);}
 *
 * <p>Only the last argument is required.
 *
 * <p>Some examples of valid methods would be:
 *
 * <pre class="code">public void afterThrowing(Exception ex)</pre>
 * <pre class="code">public void afterThrowing(RemoteException)</pre>
 * <pre class="code">public void afterThrowing(Method method, Object[] args, Object target, Exception ex)</pre>
 * <pre class="code">public void afterThrowing(Method method, Object[] args, Object target, ServletException ex)</pre>
 *
 * <p>This is a framework class that need not be used directly by Spring users.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see MethodBeforeAdviceInterceptor
 * @see AfterReturningAdviceInterceptor
 */
public class ThrowsAdviceInterceptor implements MethodInterceptor, AfterAdvice {

	// 方法名称
	private static final String AFTER_THROWING = "afterThrowing";

	private static final Log logger = LogFactory.getLog(ThrowsAdviceInterceptor.class);

	// 异常通知
	private final Object throwsAdvice;

	/** Methods on throws advice, keyed by exception class. */
	private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();


	/**
	 * Create a new ThrowsAdviceInterceptor for the given ThrowsAdvice.
	 * @param throwsAdvice the advice object that defines the exception handler methods
	 * (usually a {@link org.springframework.aop.ThrowsAdvice} implementation)
	 */
	public ThrowsAdviceInterceptor(Object throwsAdvice) {
		Assert.notNull(throwsAdvice, "Advice must not be null");
		this.throwsAdvice = throwsAdvice;

		Method[] methods = throwsAdvice.getClass().getMethods();
		for (Method method : methods) {
			// 查询指定名称的方法 afterThrowing
			if (method.getName().equals(AFTER_THROWING)) {
				Class<?> throwableParam = null;
				// 校验参数，1个参数时，必须是Throwable类型的
				if (method.getParameterCount() == 1) {
					// just a Throwable parameter
					throwableParam = method.getParameterTypes()[0];
					if (!Throwable.class.isAssignableFrom(throwableParam)) {
						throw new AopConfigException("Invalid afterThrowing signature: " +
								"single argument must be a Throwable subclass");
					}
				}
				// 4个参数时，必须是Method, Object[], target, Throwable类型的，第三个参数如果是Throwable就会报错
				else if (method.getParameterCount() == 4) {
					// Method, Object[], target, throwable
					Class<?>[] paramTypes = method.getParameterTypes();
					if (!Method.class.equals(paramTypes[0]) || !Object[].class.equals(paramTypes[1]) ||
							Throwable.class.equals(paramTypes[2]) || !Throwable.class.isAssignableFrom(paramTypes[3])) {
						throw new AopConfigException("Invalid afterThrowing signature: " +
								"four arguments must be Method, Object[], target, throwable: " + method);
					}
					throwableParam = paramTypes[3];
				}
				// 其他情况的参数会导致throwableParam== null， 直接报错
				if (throwableParam == null) {
					throw new AopConfigException("Unsupported afterThrowing signature: single throwable argument " +
							"or four arguments Method, Object[], target, throwable expected: " + method);
				}
				// 缓存异常：方法。如果多个方法处理同一个异常，就会报错
				Method existingMethod = this.exceptionHandlerMap.put(throwableParam, method);
				if (existingMethod != null) {
					throw new AopConfigException("Only one afterThrowing method per specific Throwable subclass " +
							"allowed: " + method + " / " + existingMethod);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Found exception handler method on throws advice: " + method);
				}
			}
		}

		// 如果异常通知没有合适的处理异常的方法，也会报错
		if (this.exceptionHandlerMap.isEmpty()) {
			throw new AopConfigException(
					"At least one handler method must be found in class [" + throwsAdvice.getClass() + "]");
		}
	}


	/**
	 * Return the number of handler methods in this advice.
	 */
	public int getHandlerMethodCount() {
		return this.exceptionHandlerMap.size();
	}


	@Override
	@Nullable
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		}
		catch (Throwable ex) {
			// 方法出现异常时，catch异常，查询异常处理器来处理异常，最后将该异常抛出
			Method handlerMethod = getExceptionHandler(ex);
			// 有对应的异常处理方法才进行处理
			if (handlerMethod != null) {
				invokeHandlerMethod(mi, ex, handlerMethod);
			}
			throw ex;
		}
	}

	/**
	 * Determine the exception handle method for the given exception.
	 * @param exception the exception thrown
	 * @return a handler for the given exception type, or {@code null} if none found
	 */
	@Nullable
	private Method getExceptionHandler(Throwable exception) {
		Class<?> exceptionClass = exception.getClass();
		if (logger.isTraceEnabled()) {
			logger.trace("Trying to find handler for exception of type [" + exceptionClass.getName() + "]");
		}
		Method handler = this.exceptionHandlerMap.get(exceptionClass);
		// 如果handler为空，则遍历父类，查询父类是否有合适的异常处理器
		while (handler == null && exceptionClass != Throwable.class) {
			exceptionClass = exceptionClass.getSuperclass();
			handler = this.exceptionHandlerMap.get(exceptionClass);
		}
		if (handler != null && logger.isTraceEnabled()) {
			logger.trace("Found handler for exception of type [" + exceptionClass.getName() + "]: " + handler);
		}
		return handler;
	}

	private void invokeHandlerMethod(MethodInvocation mi, Throwable ex, Method method) throws Throwable {
		Object[] handlerArgs;
		// 根据参数个数，决定传递的参数
		if (method.getParameterCount() == 1) {
			// 一个参数时，传递ex异常对象
			handlerArgs = new Object[] {ex};
		}
		else {
			// 四个参数时，传递4个参数 method args target ex
			handlerArgs = new Object[] {mi.getMethod(), mi.getArguments(), mi.getThis(), ex};
		}
		try {
			// 反射调用方法
			method.invoke(this.throwsAdvice, handlerArgs);
		}
		catch (InvocationTargetException targetEx) {
			throw targetEx.getTargetException();
		}
	}

}
