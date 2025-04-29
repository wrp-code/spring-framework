package com.wrp.spring.lesson003.readwrite.base;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025年04月29日 14:51
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ReadWriteConfiguration.class) //@1
public @interface EnableReadWrite {
}
