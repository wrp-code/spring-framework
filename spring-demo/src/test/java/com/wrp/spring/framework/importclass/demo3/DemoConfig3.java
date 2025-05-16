package com.wrp.spring.framework.importclass.demo3;

import com.wrp.spring.framework.importclass.demo3.module1.ModuleConfig1;
import com.wrp.spring.framework.importclass.demo3.module2.ModuleConfig2;
import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年05月15日 11:15
 **/
@Import({ModuleConfig1.class, ModuleConfig2.class})
public class DemoConfig3 {
}
