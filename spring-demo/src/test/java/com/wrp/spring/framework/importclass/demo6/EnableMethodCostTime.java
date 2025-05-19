package com.wrp.spring.framework.importclass.demo6;

import com.wrp.spring.lesson001.componentscan.importclass.costtime.MethodCostTimeImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025-05-19 07:53
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MethodCostTimeImportSelector.class)
public @interface EnableMethodCostTime {
}
