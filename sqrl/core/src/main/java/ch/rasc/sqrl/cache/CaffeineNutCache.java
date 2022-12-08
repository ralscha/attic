package ch.rasc.sqrl.cache;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

public class CaffeineNutCache implements NutCache {

	private final Cache<String, ExpiryEntry> cache = Caffeine.newBuilder()
			.expireAfter(new Expiry<String, ExpiryEntry>() {
				@Override
				public long expireAfterCreate(String key, ExpiryEntry value,
						long currentTime) {
					return value.getExpiry().toNanos();
				}

				@Override
				public long expireAfterUpdate(String key, ExpiryEntry value,
						long currentTime, long currentDuration) {
					return currentDuration;
				}

				@Override
				public long expireAfterRead(String key, ExpiryEntry value,
						long currentTime, long currentDuration) {
					return currentDuration;
				}
			}).build();

	private class ExpiryEntry {
		private final NutCacheEntry value;
		private final Duration expiry;

		public ExpiryEntry(NutCacheEntry value, Duration expiry) {
			this.value = value;
			this.expiry = expiry;
		}

		public NutCacheEntry getValue() {
			return this.value;
		}

		public Duration getExpiry() {
			return this.expiry;
		}
	}

	private final Duration nutExpiration;

	public CaffeineNutCache(Duration nutExpiration) {
		this.nutExpiration = nutExpiration;
	}

	@Override
	public NutCacheEntry get(String nut) {
		ExpiryEntry entry = this.cache.getIfPresent(nut);
		if (entry != null) {
			return entry.getValue();
		}
		return null;
	}

	@Override
	public NutCacheEntry getAndDelete(String nut) {
		ExpiryEntry entry = this.cache.getIfPresent(nut);
		if (entry != null) {
			this.cache.invalidate(nut);
			return entry.getValue();
		}
		return null;
	}

	@Override
	public void save(String nut, NutCacheEntry value) {
		this.cache.put(nut, new ExpiryEntry(value, this.nutExpiration));
	}

}
