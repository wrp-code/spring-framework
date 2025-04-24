package com.wrp.spring.lesson001.componentscan;

import com.wrp.spring.lesson001.componentscan.beans.ScanClass;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wrp
 * @since 2025-04-19 08:44
 **/
@ComponentScan(basePackageClasses = ScanClass.class)
public class ScanBean6 {
}
