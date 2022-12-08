package ch.rasc.sqrl.hoard;

import java.time.Duration;

public interface Hoard {

	HoardCache get(String nut);

	HoardCache getAndDelete(String nut);

	void save(String nut, HoardCache value, Duration expiration);

}
