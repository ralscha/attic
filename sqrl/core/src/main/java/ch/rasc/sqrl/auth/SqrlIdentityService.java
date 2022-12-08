package ch.rasc.sqrl.auth;

/**
 * Stores SQRL identities
 */
public interface SqrlIdentityService {

	SqrlIdentity find(String identityKey);

	SqrlIdentity findInPreviousIdentityKeys(String identityKey);

	void newIdentity(String identityKey, String serverUnlockKey, String verifyUnlockKey);

	void swap(String previousIdentityKey, String newIdentityKey);

	void disable(String identityKey);

	void enable(String identityKey);

	void remove(String identityKey);

}
