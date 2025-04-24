package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年04月21日 14:49
 **/
@Import({Service1.class, MyConfig.class, MyConfig2.class})
public class MainConfig9 {
}
