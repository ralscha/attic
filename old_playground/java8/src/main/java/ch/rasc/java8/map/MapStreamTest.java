package ch.rasc.java8.map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.speedment.common.mapstream.MapStream;

public class MapStreamTest {

	public static void main(String[] args) {
		Map<String, Integer> numberOfCats = new HashMap<>();

		numberOfCats.put("Anne", 3);
		numberOfCats.put("Berty", 1);
		numberOfCats.put("Cecilia", 1);
		numberOfCats.put("Denny", 0);
		numberOfCats.put("Erica", 0);
		numberOfCats.put("Fiona", 2);

		System.out.println(MapStream.of(numberOfCats).filterValue(v -> v > 0)
				.sortedByValue(Integer::compareTo).mapKey(k -> k + " has ")
				.mapValue(v -> v + (v == 1 ? " cat." : " cats.")).map((k, v) -> k + v)
				.collect(Collectors.joining("\n")));

	}

}
