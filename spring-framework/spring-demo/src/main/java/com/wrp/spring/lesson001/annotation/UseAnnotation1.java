package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 10:36
 **/
@Ann1
public class UseAnnotation1 {

}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann1 { //@1
}