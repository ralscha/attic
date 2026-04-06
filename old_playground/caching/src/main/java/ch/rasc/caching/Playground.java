package ch.rasc.caching;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Playground {

	public static void main(String[] args) {
		Cache<String, Optional<String>> cache = CacheBuilder.newBuilder()
				.maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build();

		cache.put("one", Optional.of("1"));
		cache.put("two", Optional.<String>absent());
		cache.put("three", Optional.of("3"));
		cache.put("four", Optional.of("4"));

		System.out.println(cache.getIfPresent("one"));
		System.out.println(cache.getIfPresent("six"));
	}

}
