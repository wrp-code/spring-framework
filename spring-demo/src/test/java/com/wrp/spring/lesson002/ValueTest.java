package com.wrp.spring.lesson002;

import com.wrp.spring.lesson002.value.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025年04月23日 15:28
 **/
public class ValueTest {
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(DbConfig.class);
		context.refresh();

		DbConfig dbConfig = context.getBean(DbConfig.class);
		System.out.println(dbConfig);
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		/*下面这段是关键 start*/
		//模拟从db中获取配置信息
		Map<String, Object> mailInfoFromDb = DbUtils.getMailInfoFromDb();
		//将其丢在MapPropertySource中（MapPropertySource类是spring提供的一个类，是PropertySource的子类）
		MapPropertySource mailPropertySource = new MapPropertySource("mail", mailInfoFromDb);
		//将mailPropertySource丢在Environment中的PropertySource列表的第一个中，让优先级最高
		context.getEnvironment().getPropertySources().addFirst(mailPropertySource);
		/*上面这段是关键 end*/

		context.register(MainConfig2.class);
		context.refresh();
		MailConfig mailConfig = context.getBean(MailConfig.class);
		System.out.println(mailConfig);
	}

	@Test
	public void test3() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//将自定义作用域注册到spring容器中
		context.getBeanFactory().registerScope(BeanMyScope.SCOPE_MY, new BeanMyScope());//@1
		context.register(MainConfig3.class);
		context.refresh();

		System.out.println("从容器中获取User对象");
		User user = context.getBean(User.class); //@2
		System.out.println("user对象的class为：" + user.getClass()); //@3

		System.out.println("多次调用user的getUsername感受一下效果\n");
		for (int i = 1; i <= 3; i++) {
			System.out.println(String.format("********\n第%d次开始调用getUsername", i));
			System.out.println(user.getUsername());
			System.out.println(String.format("第%d次调用getUsername结束\n********\n", i));
		}
	}

	@Test
	public void test4() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getBeanFactory().registerScope(BeanRefreshScope.SCOPE_REFRESH, BeanRefreshScope.getInstance());
		context.register(MainConfig4.class);
		//刷新mail的配置到Environment
		RefreshConfigUtil.refreshMailPropertySource(context);
		context.refresh();

		MailService mailService = context.getBean(MailService.class);
		System.out.println("配置未更新的情况下,输出3次");
		for (int i = 0; i < 3; i++) { //@1
			System.out.println(mailService);
			TimeUnit.MILLISECONDS.sleep(200);
		}

		System.out.println("模拟3次更新配置效果");
		for (int i = 0; i < 3; i++) { //@2
			RefreshConfigUtil.updateDbConfig(context); //@3
			System.out.println(mailService);
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
}
