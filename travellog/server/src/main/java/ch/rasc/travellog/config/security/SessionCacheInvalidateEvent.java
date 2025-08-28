package ch.rasc.travellog.config.security;

import java.util.Collection;
import java.util.Set;

public class SessionCacheInvalidateEvent {
	private final Set<Long> userIds;

	private final Set<String> sessionIds;

	private SessionCacheInvalidateEvent(Set<Long> userIds, Set<String> sessionIds) {
		this.userIds = userIds;
		this.sessionIds = sessionIds;
	}

	public static SessionCacheInvalidateEvent ofUserId(long userId) {
		return new SessionCacheInvalidateEvent(Set.of(userId), null);
	}

	public static SessionCacheInvalidateEvent ofSessionIds(
			Collection<String> sessionIds) {
		return new SessionCacheInvalidateEvent(null, Set.copyOf(sessionIds));
	}

	public static SessionCacheInvalidateEvent ofSessionId(String sessionId) {
		return new SessionCacheInvalidateEvent(null, Set.of(sessionId));
	}

	Set<Long> getUserIds() {
		return this.userIds;
	}

	Set<String> getSessionIds() {
		return this.sessionIds;
	}

}
