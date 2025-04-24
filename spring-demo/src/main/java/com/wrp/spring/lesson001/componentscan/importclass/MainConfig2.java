package com.wrp.spring.lesson001.componentscan.importclass;

import org.springframework.context.annotation.Import;

/**
 * 导入配置类
 * @author wrp
 * @since 2025年04月21日 14:12
 **/
@Import({ConfigModule1.class, ConfigModule2.class}) //@1
public class MainConfig2 {
}
