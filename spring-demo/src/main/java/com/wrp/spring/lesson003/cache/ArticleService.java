package com.wrp.spring.lesson003.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wrp
 * @since 2025年04月27日 11:10
 **/
// 提取公共参数， 方法中的注解可以覆盖
@CacheConfig(cacheNames = "cache1")
@Component
public class ArticleService {

	Map<Long, String> articleMap = new HashMap<>();

	// key默认生成策略SimpleKeyGenerator
	@Cacheable(value = "cache1")
	public List<String> list() {
		System.out.println("获取文章列表!");
		return Arrays.asList("spring", "mysql", "java高并发", "maven");
	}

	@Cacheable(cacheNames = {"cache1"}, key = "#root.target.class.name+'-'+#page+'-'+#pageSize")
	public String getPage(int page, int pageSize) {
		String msg = String.format("page-%s-pageSize-%s", page, pageSize);
		System.out.println("从db中获取数据：" + msg);
		return msg;
	}

	// condition 判断是否走缓存
	@Cacheable(cacheNames = "cache1", key = "'getById'+#id", condition = "#cache")
	public String getById(Long id, boolean cache) {
		System.out.println("获取数据!");
		return "spring缓存:" + UUID.randomUUID().toString();
	}

	// unless = true 不缓存结果
	@Cacheable(cacheNames = "cache1", key = "'findById'+#id", unless = "#result==null")
	public String findById(Long id) {
		this.articleMap.put(1L, "spring系列");
		System.out.println("----获取文章:" + id);
		return articleMap.get(id);
	}

	// 新增缓存
	@CachePut(cacheNames = "cache1", key = "'findById'+#id")
	public String add(Long id, String content) {
		System.out.println("新增文章:" + id);
		this.articleMap.put(id, content);
		return content;
	}

	// 删除缓存
	@CacheEvict(cacheNames = "cache1", key = "'findById'+#id") //@1
	public void delete(Long id) {
		System.out.println("删除文章：" + id);
		this.articleMap.remove(id);
	}
}
