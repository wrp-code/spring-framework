package com.wrp.spring.lesson001.annotation;

/**
 * @author wrp
 * @since 2025年04月18日 10:11
 **/
public @interface MyAnnotation {
	// 参数类型只能是基本数据类型、String、Class、枚举类型、注解类型（体现了注解的嵌套效果）以及上述类型的一位数组
	// 只有一个参数
//	[public] 参数类型 参数名称1() [default 参数默认值];
}
