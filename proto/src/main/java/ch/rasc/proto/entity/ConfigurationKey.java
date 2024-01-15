package ch.rasc.proto.entity;

public enum ConfigurationKey {
	LOGIN_LOCK_ATTEMPTS("loginLockAttempts"), LOGIN_LOCK_MINUTES("loginLockMinutes"), LOG_LEVEL(
			"logLevel");

	private final String jsonName;

	private ConfigurationKey(String jsonName) {
		this.jsonName = jsonName;
	}

	public String getJsonName() {
		return this.jsonName;
	}

}
