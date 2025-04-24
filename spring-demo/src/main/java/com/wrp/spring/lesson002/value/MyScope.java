package com.wrp.spring.lesson002.value;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025年04月23日 15:42
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(BeanMyScope.SCOPE_MY) //@1
public @interface MyScope {
	/**
	 * @see Scope#proxyMode()
	 */
	ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;//@2
}