package ch.rasc.caching.web;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SpringBootApplication
@EnableCaching
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		Cache<Object, Object> tenMinutesCache = CacheBuilder.newBuilder()
				.expireAfterWrite(10, TimeUnit.MINUTES).build();

		Cache<Object, Object> maxSizeCache = CacheBuilder.newBuilder().maximumSize(10)
				.build();

		cacheManager.setCaches(
				Arrays.asList(new GuavaCache("tenMinutesCache", tenMinutesCache),
						new GuavaCache("maxSizeCache", maxSizeCache)));

		return cacheManager;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
