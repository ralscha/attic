package ch.rasc.sqrl.hoard;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

public class MapHoard implements Hoard {

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
		private final HoardCache value;
		private final Duration expiry;

		public ExpiryEntry(HoardCache value, Duration expiry) {
			this.value = value;
			this.expiry = expiry;
		}

		public HoardCache getValue() {
			return this.value;
		}

		public Duration getExpiry() {
			return this.expiry;
		}
	}

	@Override
	public HoardCache get(String nut) {
		ExpiryEntry entry = this.cache.getIfPresent(nut);
		if (entry != null) {
			return entry.getValue();
		}
		return null;
	}

	@Override
	public HoardCache getAndDelete(String nut) {
		ExpiryEntry entry = this.cache.getIfPresent(nut);
		if (entry != null) {
			this.cache.invalidate(nut);
			return entry.getValue();
		}
		return null;
	}

	@Override
	public void save(String nut, HoardCache value, Duration expiration) {
		this.cache.put(nut, new ExpiryEntry(value, expiration));
	}

}
