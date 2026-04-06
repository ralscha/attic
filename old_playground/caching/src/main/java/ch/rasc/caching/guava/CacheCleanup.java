package ch.rasc.caching.guava;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.stereotype.Service;

@Service
public class CacheCleanup {

	private ScheduledExecutorService scheduler;

	@Autowired
	CacheManager cacheManager;

	@PreDestroy
	public void shutdown() {
		this.scheduler.shutdown();
	}

	@PostConstruct
	public void cacheCleanup() {
		this.scheduler = Executors.newScheduledThreadPool(1);
		this.scheduler.scheduleWithFixedDelay(() -> {
			for (String cacheName : this.cacheManager.getCacheNames()) {
				System.out.println(">> cleanup " + cacheName);
				((GuavaCache) this.cacheManager.getCache(cacheName)).getNativeCache()
						.cleanUp();
			}
		}, 5, 20, TimeUnit.SECONDS);

	}
}
