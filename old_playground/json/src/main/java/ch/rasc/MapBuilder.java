package ch.rasc;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

	private final Map<K, V> map;

	public MapBuilder() {
		this.map = new HashMap<>();
	}

	public static <K, V> MapBuilder<K, V> builder() {
		return new MapBuilder<>();
	}

	public MapBuilder(Class<? extends Map<K, V>> c)
			throws IllegalAccessException, InstantiationException {
		this.map = c.newInstance();
	}

	public MapBuilder(Map<K, V> m) {
		this.map = m;
	}

	public MapBuilder<K, V> put(K k, V v) {
		this.map.put(k, v);
		return this;
	}

	public MapBuilder<K, V> p(K k, V v) {
		return put(k, v);
	}

	public MapBuilder<K, V> put(Map.Entry<? extends K, ? extends V> e) {
		return put(e.getKey(), e.getValue());
	}

	public MapBuilder<K, V> p(Map.Entry<? extends K, ? extends V> e) {
		return put(e);
	}

	public MapBuilder<K, V> putAll(Map<? extends K, ? extends V> m) {
		this.map.putAll(m);
		return this;
	}

	public MapBuilder<K, V> pa(Map<? extends K, ? extends V> m) {
		return putAll(m);
	}

	public MapBuilder<K, V> remove(K k) {
		this.map.remove(k);
		return this;
	}

	public MapBuilder<K, V> r(K k) {
		return remove(k);
	}

	public MapBuilder<K, V> remove(Map.Entry<? extends K, ? extends V> e) {
		return remove(e.getKey());
	}

	public MapBuilder<K, V> r(Map.Entry<? extends K, ? extends V> e) {
		return remove(e);
	}

	public Map<K, V> build() {
		return this.map;
	}
}