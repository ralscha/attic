package ch.rasc.sqrl.auth;

/**
 * Stores SQRL identities
 */
public interface AuthStore {

	SqrlIdentity findIdentity(String idk);

	void saveIdentity(SqrlIdentity identity);

	void deleteIdentity(String idk);

}
