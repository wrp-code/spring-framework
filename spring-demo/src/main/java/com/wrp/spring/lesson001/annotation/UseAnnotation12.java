package com.wrp.spring.lesson001.annotation;

/**
 * @author wrp
 * @since 2025年04月18日 11:24
 **/
@Ann12(name = "路人甲Java")
@Ann12(name = "Spring系列")
public class UseAnnotation12 {
	@Ann12s(
			{@Ann12(name = "Java高并发系列，见公众号"),
					@Ann12(name = "mysql高手系列，见公众号")}
	)
	private String v1;
}