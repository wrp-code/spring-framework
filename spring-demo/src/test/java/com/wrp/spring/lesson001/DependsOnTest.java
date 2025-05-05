package com.wrp.spring.lesson001;

import com.wrp.spring.lesson001.dependon.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年05月05日 7:05
 */
public class DependsOnTest {

	// 创建时根据声明的顺序，销毁顺序与创建时相反
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig1.class);
		context.close();
	}

	// 创建顺序未知，销毁顺序与创建时相反
	@Test
	public void test2() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig2.class);
		context.close();
	}

	// 被依赖的先创建，销毁顺序与创建时相反
	@Test
	public void test3() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig3.class);
		context.close();
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig4.class);
		context.close();
	}

	// 创建顺序是声明时的顺序，销毁顺序与创建顺序相反
	@Test
	public void test5() {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MainConfig5.class);
		context.close();
	}
}
