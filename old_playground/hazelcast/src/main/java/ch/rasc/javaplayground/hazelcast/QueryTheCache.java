package ch.rasc.javaplayground.hazelcast;

import java.util.Set;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

public class QueryTheCache {

	private final HazelcastInstance haz;

	public QueryTheCache() {
		this.haz = Hazelcast.newHazelcastInstance();
	}

	public void queryMap() {

		IMap<Predicate<?, ?>, ObjectToCache> map = this.haz.getMap("testmap");
		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate<?, ?> predicate = e.get("objectName")
				.in("example: 1", "example: 2", "example: 3")
				.and(e.get("objectID").lessThan(4));
		Set<ObjectToCache> objects = (Set<ObjectToCache>) map.values(predicate);
		for (ObjectToCache o : objects) {
			System.out.println("Map Query Result :" + o);
		}
	}

	public static void main(String[] args) {
		QueryTheCache use = new QueryTheCache();
		use.queryMap();
	}

}