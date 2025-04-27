package com.wrp.spring.lesson003.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author wrp
 * @since 2025年04月27日 12:27
 **/
@ComponentScan
@EnableCaching
public class MainConfig2 {
	@Bean //@2
	public CacheManager cacheManager() throws IOException {
		RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(this.redissonClient());
		cacheManager.setCacheNames(Arrays.asList("cache2"));
		return cacheManager;
	}

	@Bean //@3
	public RedissonClient redissonClient() throws IOException {
		InputStream is = MainConfig2.class.getResourceAsStream("/lesson003/redis.yml");
		Config config = Config.fromYAML(is);
		return Redisson.create(config);
	}
}