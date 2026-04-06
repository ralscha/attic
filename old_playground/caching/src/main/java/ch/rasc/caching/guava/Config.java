package ch.rasc.caching.guava;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
@ComponentScan(basePackages = { "ch.rasc.caching.guava" })
public class Config {

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		Cache<Object, Object> oneMinuteCache = CacheBuilder.newBuilder()
				.expireAfterWrite(1, TimeUnit.MINUTES).build();

		Cache<Object, Object> maxSizeCache = CacheBuilder.newBuilder().maximumSize(10)
				.build();

		cacheManager
				.setCaches(Arrays.asList(new GuavaCache("oneMinuteCache", oneMinuteCache),
						new GuavaCache("maxSizeCache", maxSizeCache)));

		return cacheManager;
	}

}