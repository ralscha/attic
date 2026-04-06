package ch.rasc.javaplayground.hazelcast;

import java.util.List;
import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class UseTheCache {

	private final HazelcastInstance haz;

	public UseTheCache() {
		this.haz = Hazelcast.newHazelcastInstance();
	}

	public void readList() {
		List<ObjectToCache> list = this.haz.getList("testlist");
		System.out.println("List has " + list.size() + " items.");
	}

	public void readMap() {
		Map<Integer, ObjectToCache> map = this.haz.getMap("testmap");
		System.out.println("Map has " + map.size() + " items.");

	}

	public static void main(String[] args) {
		UseTheCache use = new UseTheCache();
		use.readList();
		use.readMap();
	}
}