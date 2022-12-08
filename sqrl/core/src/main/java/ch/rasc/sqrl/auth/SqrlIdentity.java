package ch.rasc.sqrl.auth;

public interface SqrlIdentity {

	String getIdentityKey();

	String getServerUnlockKey();

	String getVerifyUnlockKey();

	boolean isDisabled();
}
