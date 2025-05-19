package com.wrp.spring.framework.importclass.demo5;

import com.wrp.spring.framework.importclass.demo5.module1.ModuleConfig1;
import com.wrp.spring.framework.importclass.demo5.module2.ModuleConfig2;
import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025-05-19 07:40
 **/
@Import({ModuleConfig1.class, ModuleConfig2.class})
public class DemoConfig5 {
}
