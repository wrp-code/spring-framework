package com.wrp.spring.lesson003.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author wrp
 * @since 2025年04月27日 12:29
 **/
@Component
public class BookService {

	@Cacheable(cacheNames = "cache2", key = "#root.targetClass.name+'-'+#root.method.name")
	public List<String> list() {
		System.out.println("---模拟从db中获取数据---");
		return Arrays.asList("java高并发", "springboot", "springcloud");
	}

}