package com.wrp.spring.lesson002.messagesource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

/**
 * @author wrp
 * @since 2025年04月23日 19:59
 **/
public class MessageSourceFromDb extends StaticMessageSource implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		//此处我们在当前bean初始化之后，模拟从db中获取国际化信息，然后调用addMessage来配置国际化信息
		this.addMessage("desc", Locale.CHINA, "我是从db来的信息");
		this.addMessage("desc", Locale.UK, "MessageSource From Db");
	}
}