package ch.rasc.sqrl.cache;

public interface NutCache {

	NutCacheEntry get(String nut);

	NutCacheEntry getAndDelete(String nut);

	void save(String nut, NutCacheEntry value);

}
