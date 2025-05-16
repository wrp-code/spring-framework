package com.wrp.spring.framework.importclass.demo2;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年05月15日 9:36
 **/
@Import({MyConfig.class, MyConfig2.class})
public class DemoConfig2 {
}
