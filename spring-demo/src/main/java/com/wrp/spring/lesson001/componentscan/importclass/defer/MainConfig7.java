package com.wrp.spring.lesson001.componentscan.importclass.defer;

import org.springframework.context.annotation.Import;

/**
 * @author wrp
 * @since 2025年04月21日 14:45
 **/
@Import({
		DeferredImportSelector1.class,
		Configuration3.class,
		ImportSelector1.class,
})
public class MainConfig7 {
}