package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025年04月21日 15:32
 **/
@Configuration
@Conditional({Condition1.class, Condition2.class, Condition3.class})//@5
public class MainConfig6 {
}
