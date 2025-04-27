package com.wrp.spring.framework.enable.async;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-27 07:33
 **/
public class AsyncTest {

	@Test
	public void test() throws InterruptedException {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);

		LogService logService = context.getBean(LogService.class);
		System.out.println("服务开始...");
		logService.log();
		System.out.println("服务结束...");
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test2() throws InterruptedException, ExecutionException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(Config.class);
		context.refresh();
		GoodsService goodsService = context.getBean(GoodsService.class);

		long starTime = System.currentTimeMillis();
		System.out.println("开始获取商品的各种信息");

		long goodsId = 1L;
		Future<String> goodsInfoFuture = goodsService.getGoodsInfo(goodsId);
		Future<String> goodsDescFuture = goodsService.getGoodsDesc(goodsId);
		Future<List<String>> goodsCommentsFuture = goodsService.getGoodsComments(goodsId);

		System.out.println(goodsInfoFuture.get());
		System.out.println(goodsDescFuture.get());
		System.out.println(goodsCommentsFuture.get());

		System.out.println("商品信息获取完毕,总耗时(ms)：" + (System.currentTimeMillis() - starTime));

		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}

}
