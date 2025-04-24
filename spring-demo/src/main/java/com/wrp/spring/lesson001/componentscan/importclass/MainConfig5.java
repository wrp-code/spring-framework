package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Import;

/**
 * 导入ImportSelector接口实现类
 * @author wrp
 * @since 2025年04月21日 14:28
 **/
@Import({MyImportSelector.class})
public class MainConfig5 {
}
