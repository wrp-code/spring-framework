package com.wrp.spring.lesson002.value;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025年04月23日 15:49
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Scope(BeanRefreshScope.SCOPE_REFRESH)
@Documented
public @interface RefreshScope {
	ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS; //@1
}