package com.wrp.spring.lesson001.componentscan.importclass;

import com.wrp.spring.lesson001.componentscan.importclass.scan.module1.ComponentScanModule1;
import com.wrp.spring.lesson001.componentscan.importclass.scan.module2.ComponentScanModule2;
import org.springframework.context.annotation.Import;

/**
 * 导入扫描类
 * @author wrp
 * @since 2025年04月21日 14:14
 **/
@Import({ComponentScanModule1.class, ComponentScanModule2.class})
public class MainConfig3 {
}
