package com.wrp.spring.lesson003.aop;

import com.wrp.spring.lesson003.cache.ArticleService;
import com.wrp.spring.lesson003.cache.BookService;
import com.wrp.spring.lesson003.cache.MainConfig1;
import com.wrp.spring.lesson003.cache.MainConfig2;
import org.junit.jupiter.api.Test;
import org.redisson.spring.cache.RedissonCache;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wrp
 * @since 2025年04月27日 11:16
 **/
public class CacheTest {

	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);
		System.out.println(articleService.list());
		System.out.println(articleService.list());
	}

	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);

		//page=1,pageSize=10调用2次
		System.out.println(articleService.getPage(1, 10));
		System.out.println(articleService.getPage(1, 10));

		//page=2,pageSize=10调用2次
		System.out.println(articleService.getPage(2, 10));
		System.out.println(articleService.getPage(2, 10));

		{
			System.out.println("下面打印出cache1缓存中的key列表");
			ConcurrentMapCacheManager cacheManager = context.getBean(ConcurrentMapCacheManager.class);
			ConcurrentMapCache cache1 = (ConcurrentMapCache) cacheManager.getCache("cache1");
			cache1.getNativeCache().keySet().stream().forEach(System.out::println);
		}
	}

	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);

		System.out.println(articleService.getById(1L, true));
		System.out.println(articleService.getById(1L, true));
		System.out.println(articleService.getById(1L, false));
		System.out.println(articleService.getById(1L, true));
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);

		System.out.println(articleService.findById(1L));
		System.out.println(articleService.findById(1L));
		System.out.println(articleService.findById(3L));
		System.out.println(articleService.findById(3L));

		{
			System.out.println("下面打印出缓cache1缓存中的key列表");
			ConcurrentMapCacheManager cacheManager = context.getBean(ConcurrentMapCacheManager.class);
			ConcurrentMapCache cache1 = (ConcurrentMapCache) cacheManager.getCache("cache1");
			cache1.getNativeCache().keySet().stream().forEach(System.out::println);
		}
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);

		//新增3个文章，由于add方法上面有@CachePut注解，所以新增之后会自动丢到缓存中
		articleService.add(1L, "java高并发系列");
		articleService.add(2L, "Maven高手系列");
		articleService.add(3L, "MySQL高手系列");

		//然后调用findById获取，看看是否会走缓存
		System.out.println("调用findById方法，会尝试从缓存中获取");
		System.out.println(articleService.findById(1L));
		System.out.println(articleService.findById(2L));
		System.out.println(articleService.findById(3L));

		{
			System.out.println("下面打印出cache1缓存中的key列表");
			ConcurrentMapCacheManager cacheManager = context.getBean(ConcurrentMapCacheManager.class);
			ConcurrentMapCache cache1 = (ConcurrentMapCache) cacheManager.getCache("cache1");
			cache1.getNativeCache().keySet().stream().forEach(System.out::println);
		}
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MainConfig1.class);
		context.refresh();
		ArticleService articleService = context.getBean(ArticleService.class);

		//第1次调用findById，缓存中没有，则调用方法，将结果丢到缓存中
		System.out.println(articleService.findById(1L));
		//第2次调用findById，缓存中存在，直接从缓存中获取
		System.out.println(articleService.findById(1L));

		//执行删除操作，delete方法上面有@CacheEvict方法，会清除缓存
		articleService.delete(1L);

		//再次调用findById方法，发现缓存中没有了，则会调用目标方法
		System.out.println(articleService.findById(1L));
	}

	@Test
	public void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);

		BookService bookService = context.getBean(BookService.class);
		System.out.println(bookService.list());
		System.out.println(bookService.list());

		{
			System.out.println("下面打印出cache1缓存中的key列表");
			RedissonSpringCacheManager cacheManager = context.getBean(RedissonSpringCacheManager.class);
			RedissonCache cache1 = (RedissonCache) cacheManager.getCache("cache2");
			cache1.getNativeCache().keySet().stream().forEach(System.out::println);
		}
	}
}