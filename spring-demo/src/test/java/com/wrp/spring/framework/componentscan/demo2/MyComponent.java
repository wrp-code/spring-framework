package com.wrp.spring.framework.componentscan.demo2;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025-05-16 07:30
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {

	@AliasFor(annotation = Component.class)
	String value() default  "";
}
