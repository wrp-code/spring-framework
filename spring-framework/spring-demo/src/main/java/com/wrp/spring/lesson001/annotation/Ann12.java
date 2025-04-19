package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025年04月18日 11:59
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Repeatable(Ann12s.class)
public @interface Ann12{
	String name();
}
