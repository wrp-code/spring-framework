package com.wrp.spring.framework.componentscan.demo1;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025-05-16 07:30
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
}
