package com.wrp.spring.lesson002.life;

import java.lang.annotation.*;

/**
 * @author wrp
 * @since 2025-04-21 22:44
 **/
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
}
