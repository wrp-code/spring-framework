package com.wrp.spring.lesson001.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月18日 10:58
 **/
@Target({ElementType.PACKAGE,
		ElementType.TYPE,
		ElementType.FIELD,
		ElementType.CONSTRUCTOR,
		ElementType.METHOD,
		ElementType.PARAMETER,
		ElementType.TYPE_PARAMETER,
		ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann11 {
	String value();
}

@Target({ElementType.PACKAGE,
		ElementType.TYPE,
		ElementType.FIELD,
		ElementType.CONSTRUCTOR,
		ElementType.METHOD,
		ElementType.PARAMETER,
		ElementType.TYPE_PARAMETER,
		ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann11_0 {
	int value();
}

@Ann11("用在了类上")
@Ann11_0(0)
public class UserAnnotation11<@Ann11("用在了类变量类型V1上") @Ann11_0(1) V1, @Ann11("用在了类变量类型V2上") @Ann11_0(2) V2> {
	@Ann11("用在了字段上")
	@Ann11_0(3)
	private String name;

	private Map<@Ann11("用在了泛型类型上,String") @Ann11_0(4) String, @Ann11("用在了泛型类型上,Integer") @Ann11_0(5) Integer> map;

	@Ann11("用在了构造方法上")
	@Ann11_0(6)
	public UserAnnotation11() {
		this.name = name;
	}

	@Ann11("用在了返回值上")
	@Ann11_0(7)
	public String m1(@Ann11("用在了参数上") @Ann11_0(8) String name) {
		return null;
	}

}