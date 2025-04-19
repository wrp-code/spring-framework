package com.wrp.spring.lesson001.componentscan;

import org.springframework.context.annotation.ComponentScan;

/**
 * 扫描指定包
 * 问题：
 * 字符串配置，如果后期修改了包路径，则需要对应调整
 * @author wrp
 * @since 2025-04-19 08:40
 **/
@ComponentScan({
		"com.wrp.spring.lesson001.componentscan.controller",
		"com.wrp.spring.lesson001.componentscan.service"
})
public class ScanBean2 {
}
