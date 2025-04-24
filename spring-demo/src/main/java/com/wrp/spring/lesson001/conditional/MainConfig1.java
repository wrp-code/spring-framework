package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年04月21日 15:21
 **/
@Import({BeanConfig1.class,BeanConfig2.class}) //@1
public class MainConfig1 {
}
