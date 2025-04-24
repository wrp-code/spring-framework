package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Conditional;

/**
 * @author wrp
 * @since 2025年04月21日 15:29
 **/
@Conditional({Condition1.class, Condition2.class, Condition3.class})
public class MainConfig5 {
}
