package ch.rasc.sqrl.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapAuthStore implements AuthStore {

	private final Map<String, SqrlIdentity> store = new ConcurrentHashMap<>();

	@Override
	public SqrlIdentity findIdentity(String idk) {
		return this.store.get(idk);
	}

	@Override
	public void saveIdentity(SqrlIdentity identity) {
		this.store.put(identity.getIdk(), identity);
	}

	@Override
	public void deleteIdentity(String idk) {
		this.store.remove(idk);
	}

}
