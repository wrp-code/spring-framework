package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wrp
 * @since 2025年04月18日 10:39
 **/
@Target(value = {
		ElementType.TYPE,
		ElementType.METHOD,
		ElementType.FIELD,
		ElementType.PARAMETER,
		ElementType.CONSTRUCTOR,
		ElementType.LOCAL_VARIABLE
})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann6 {
	String value();

	ElementType elementType();
}

@Ann6(value = "我用在类上", elementType = ElementType.TYPE)
public class UseAnnotation6 {
	@Ann6(value = "我用在字段上", elementType = ElementType.FIELD)
	private String a;

	@Ann6(value = "我用在构造方法上", elementType = ElementType.CONSTRUCTOR)
	public UseAnnotation6(@Ann6(value = "我用在方法参数上", elementType = ElementType.PARAMETER) String a) {
		this.a = a;
	}

	@Ann6(value = "我用在了普通方法上面", elementType = ElementType.METHOD)
	public void m1() {
		@Ann6(value = "我用在了本地变量上", elementType = ElementType.LOCAL_VARIABLE) String a;
	}
}