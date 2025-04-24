package com.wrp.spring.lesson001.conditional;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年04月21日 15:27
 **/
@Import({DevBeanConfig.class, TestBeanConfig.class, ProdBeanConfig.class})
public class BeanConfig3 {
}
