package ch.rasc.travellog.config.security;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static ch.rasc.travellog.db.tables.AppUser.APP_USER;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jooq.DSLContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import ch.rasc.travellog.config.AppProperties;

@Service
public class SessionCacheService {

	private final Cache<String, Ids> appSessionCache;

	private final Cache<Long, AppUserAuthentication> userDetailCache;

	private final DSLContext dsl;

	private final ConcurrentLinkedQueue<Ids> updateLastAccessQueue;

	private final Duration inactiveSessionMaxAge;

	public SessionCacheService(DSLContext dsl, AppProperties appProperties) {
		this.appSessionCache = Caffeine.newBuilder().expireAfterAccess(4, TimeUnit.HOURS)
				.maximumSize(5_000).build();

		this.userDetailCache = Caffeine.newBuilder().expireAfterAccess(4, TimeUnit.HOURS)
				.maximumSize(5_000).build();

		this.dsl = dsl;
		this.updateLastAccessQueue = new ConcurrentLinkedQueue<>();
		this.inactiveSessionMaxAge = appProperties.getInactiveSessionMaxAge();
	}

	@EventListener
	@Async
	void handleSessinCacheInvalidateEvent(SessionCacheInvalidateEvent invalidateEvent) {
		if (invalidateEvent.getUserIds() != null) {
			this.userDetailCache.invalidateAll(invalidateEvent.getUserIds());
		}

		if (invalidateEvent.getSessionIds() != null) {
			this.appSessionCache.invalidateAll(invalidateEvent.getSessionIds());
		}
	}

	@Scheduled(fixedDelayString = "PT30S")
	void updateLastAccessRunner() {
		if (!this.updateLastAccessQueue.isEmpty()) {
			Set<String> appSessionIds = new HashSet<>();
			Set<Long> appUserIds = new HashSet<>();

			Ids id;
			while ((id = this.updateLastAccessQueue.poll()) != null) {
				appSessionIds.add(id.getAppSessionId());
				appUserIds.add(id.getAppUserId());
			}

			LocalDateTime now = LocalDateTime.now();

			this.dsl.update(APP_SESSION).set(APP_SESSION.LAST_ACCESS, now)
					.where(APP_SESSION.ID.in(appSessionIds)).execute();

			this.dsl.update(APP_USER).set(APP_USER.LAST_ACCESS, now)
					.where(APP_USER.ID.in(appUserIds)).execute();
		}
	}

	public AppUserAuthentication getUserAuthentication(final String sessionId) {
		if (StringUtils.hasText(sessionId)) {

			AtomicBoolean readFromDb = new AtomicBoolean(false);
			Ids updateIds = this.appSessionCache.get(sessionId, key -> {
				var sessionRecord = this.dsl
						.select(APP_SESSION.ID, APP_SESSION.APP_USER_ID,
								APP_SESSION.LAST_ACCESS)
						.from(APP_SESSION).where(APP_SESSION.ID.eq(sessionId)).fetchOne();

				if (sessionRecord != null) {
					LocalDateTime lastAccess = sessionRecord.get(APP_SESSION.LAST_ACCESS);

					if (lastAccess.plus(this.inactiveSessionMaxAge)
							.isAfter(LocalDateTime.now())) {
						readFromDb.set(true);
						return new Ids(sessionRecord.get(APP_SESSION.APP_USER_ID),
								sessionRecord.get(APP_SESSION.ID));
					}
					// session expired
					this.dsl.delete(APP_SESSION).where(APP_SESSION.ID.eq(sessionId))
							.execute();
				}
				return null;
			});

			if (updateIds != null) {
				if (readFromDb.get()) {
					this.userDetailCache.invalidate(updateIds.getAppUserId());
				}
				AppUserAuthentication authentication = this.userDetailCache
						.get(updateIds.getAppUserId(), key -> {
							var appUserRecord = this.dsl
									.select(APP_USER.ID, APP_USER.EMAIL, APP_USER.ENABLED,
											APP_USER.EXPIRED, APP_USER.AUTHORITY)
									.from(APP_USER)
									.where(APP_USER.ID.eq(updateIds.getAppUserId()))
									.fetchOne();

							if (appUserRecord != null) {
								Boolean enabled = appUserRecord.get(APP_USER.ENABLED);
								LocalDateTime expired = appUserRecord
										.get(APP_USER.EXPIRED);

								if (enabled.booleanValue() && expired == null) {
									return new AppUserAuthentication(new AppUserDetail(
											appUserRecord.get(APP_USER.ID),
											appUserRecord.get(APP_USER.EMAIL),
											appUserRecord.get(APP_USER.AUTHORITY)));
								}
							}
							return null;
						});

				if (authentication != null) {
					this.updateLastAccessQueue.add(updateIds);
					return authentication;
				}
			}
		}

		return null;
	}

	private static class Ids {
		final long appUserId;
		final String appSessionId;

		Ids(long appUserId, String appSessionId) {
			this.appUserId = appUserId;
			this.appSessionId = appSessionId;
		}

		long getAppUserId() {
			return this.appUserId;
		}

		String getAppSessionId() {
			return this.appSessionId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.appSessionId, this.appUserId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if ((obj == null) || (getClass() != obj.getClass())) {
				return false;
			}
			Ids other = (Ids) obj;
			if (!Objects.equals(this.appSessionId, other.appSessionId)) {
				return false;
			}
			return this.appUserId == other.appUserId;
		}

	}
}
