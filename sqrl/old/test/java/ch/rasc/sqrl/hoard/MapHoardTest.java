package ch.rasc.sqrl.hoard;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapHoardTest {

	@Test
	void testGet() {
		MapHoard mh = new MapHoard();
		HoardCache hoardCache = new HoardCache();
		mh.save("nut", hoardCache, Duration.ofSeconds(1));
		
		HoardCache hoardCache2 = mh.get("nut");
		Assertions.assertSame(hoardCache, hoardCache2);
		
		hoardCache2 = mh.get("nut");
		Assertions.assertSame(hoardCache, hoardCache2);
	}

	@Test
	void testGetAndDelete() {
		MapHoard mh = new MapHoard();
		HoardCache hoardCache = new HoardCache();
		mh.save("nut", hoardCache, Duration.ofSeconds(1));
		
		HoardCache hoardCache2 = mh.getAndDelete("nut");
		Assertions.assertSame(hoardCache, hoardCache2);
		
		hoardCache2 = mh.get("nut");
		Assertions.assertNull(hoardCache2);
	}

	@Test
	void testExpiration() throws InterruptedException {
		MapHoard mh = new MapHoard();
		HoardCache hoardCache = new HoardCache();
		mh.save("nut", hoardCache, Duration.ofSeconds(1));
		
		HoardCache hoardCache2 = mh.get("nut");
		Assertions.assertSame(hoardCache, hoardCache2);
		
		TimeUnit.SECONDS.sleep(2);
		
		hoardCache2 = mh.get("nut");
		Assertions.assertNull(hoardCache2);
	}

}
