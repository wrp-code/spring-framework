package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.messagesource.MainConfig1;
import com.wrp.spring.lesson002.messagesource.MainConfig2;
import com.wrp.spring.lesson002.messagesource.MainConfig3;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025年04月23日 16:16
 **/
public class MessageSourceTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		//未指定Locale，此时系统会取默认的locale对象，本地默认的值中文【中国】，即：zh_CN
		System.out.println(context.getMessage("name", null, null));
		System.out.println(context.getMessage("name", null, Locale.CHINA)); //CHINA对应：zh_CN
		System.out.println(context.getMessage("name", null, Locale.UK)); //UK对应en_GB
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		//未指定Locale，此时系统会取默认的，本地电脑默认的值中文【中国】，即：zh_CN
		System.out.println(context.getMessage("personal_introduction", new String[]{"spring高手", "java高手"}, Locale.CHINA)); //CHINA对应：zh_CN
		System.out.println(context.getMessage("personal_introduction", new String[]{"spring", "java"}, Locale.UK)); //UK对应en_GB
	}

	@Test
	public void test3() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig2.class);
		context.refresh();
		//输出2次
		for (int i = 0; i < 3; i++) {
			System.out.println(context.getMessage("address", null, Locale.CHINA));
			TimeUnit.SECONDS.sleep(5);
		}
	}

	@Test
	public void test4() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig3.class);
		context.refresh();
		System.out.println(context.getMessage("desc", null, Locale.CHINA));
		System.out.println(context.getMessage("desc", null, Locale.UK));
	}
}
