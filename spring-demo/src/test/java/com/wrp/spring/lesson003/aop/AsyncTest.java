package com.wrp.spring.lesson003.aop;

import com.wrp.spring.lesson003.async.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-26 08:12
 **/
public class AsyncTest {
	@Test
	public void test1() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		LogService logService = context.getBean(LogService.class);
		System.out.println(Thread.currentThread() + " logService.log start," + System.currentTimeMillis());
		logService.log("异步执行方法!");
		System.out.println(Thread.currentThread() + " logService.log end," + System.currentTimeMillis());

		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test2() throws InterruptedException, ExecutionException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
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

	@Test
	public void test3() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		LogService logService = context.getBean(LogService.class);
		System.out.println(Thread.currentThread() + " logService.log start," + System.currentTimeMillis());
		logService.log("异步执行方法!");
		System.out.println(Thread.currentThread() + " logService.log end," + System.currentTimeMillis());

		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test5() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		LogService logService = context.getBean(LogService.class);
		try {
			Future<String> future = logService.mockException();
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("捕获 ExecutionException 异常");
			//通过e.getCause获取实际的异常信息
			e.getCause().printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test6() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		LogService logService = context.getBean(LogService.class);
		logService.mockNoReturnException();
		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test7() throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig5.class);
		context.refresh();

		RechargeService rechargeService = context.getBean(RechargeService.class);
		rechargeService.recharge();
		CashOutService cashOutService = context.getBean(CashOutService.class);
		cashOutService.cashOut();

		//休眠一下，防止@Test退出
		TimeUnit.SECONDS.sleep(3);
	}
}
