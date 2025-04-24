package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Import;

/**
 * 导入普通类
 * @author wrp
 * @since 2025年04月21日 14:08
 **/
@Import({Service1.class, Service2.class})
public class MainConfig1 {
}
