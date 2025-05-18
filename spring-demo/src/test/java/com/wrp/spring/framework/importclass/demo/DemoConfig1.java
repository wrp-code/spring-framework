package com.wrp.spring.framework.importclass.demo;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年05月15日 9:13
 **/
// 普通类也会作为配置类
@Import({Service.class, Service2.class})
public class DemoConfig1 { }
