package com.wrp.spring.framework.conditional.demo1;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年05月19日 11:17
 **/
@Import({DemoConfig1.class, DemoConfig2.class})
public class ImportConfig {
}
