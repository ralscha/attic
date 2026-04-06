package ch.rasc.immutable;

public class MultimapExamples {

	public static void main(String[] args) {
		// Apache Common Collections

		org.apache.commons.collections4.SetValuedMap<String, Integer> aSubscribers = new org.apache.commons.collections4.multimap.HashSetValuedHashMap<>();
		aSubscribers.put("topic.a", 1);
		aSubscribers.put("topic.a", 2);
		aSubscribers.put("topic.a", 3);
		aSubscribers.put("topic.a", 3);
		aSubscribers.put("topic.b", 1);

		System.out.println(aSubscribers.get("topic.a")); // [1, 2, 3]
		System.out.println(aSubscribers.get("topic.b")); // [1]
		System.out.println(aSubscribers.size()); // 4

		// Guava
		com.google.common.collect.HashMultimap<String, Integer> gSubscribers = com.google.common.collect.HashMultimap
				.create();
		gSubscribers.put("topic.a", 1);
		gSubscribers.put("topic.a", 2);
		gSubscribers.put("topic.a", 3);
		gSubscribers.put("topic.a", 3);
		gSubscribers.put("topic.b", 1);
		System.out.println(gSubscribers.get("topic.a")); // [1, 2, 3]
		System.out.println(gSubscribers.get("topic.b")); // [1]
		System.out.println(gSubscribers.size()); // 4

		// Eclipse Collections

		org.eclipse.collections.api.multimap.set.MutableSetMultimap<String, Integer> ecSubscribers = org.eclipse.collections.impl.multimap.set.UnifiedSetMultimap
				.newMultimap();
		ecSubscribers.put("topic.a", 1);
		ecSubscribers.put("topic.a", 2);
		ecSubscribers.put("topic.a", 3);
		ecSubscribers.put("topic.a", 3);
		ecSubscribers.put("topic.b", 1);
		System.out.println(ecSubscribers.get("topic.a")); // [1, 2, 3]
		System.out.println(ecSubscribers.get("topic.b")); // [1]
		System.out.println(ecSubscribers.size()); // 4

		// Spring
		org.springframework.util.MultiValueMap<String, Integer> springSubscribers = new org.springframework.util.LinkedMultiValueMap<>();
		springSubscribers.add("topic.a", 1);
		springSubscribers.add("topic.a", 2);
		springSubscribers.add("topic.a", 3);
		springSubscribers.add("topic.a", 3);
		springSubscribers.add("topic.b", 1);
		System.out.println(springSubscribers.get("topic.a")); // [1, 2, 3, 3]
		System.out.println(springSubscribers.get("topic.b")); // [1]
		System.out.println(springSubscribers.size()); // 2		
		
		
		// Do it yourself MultiMap
		DiyMultiMap<String,Integer> diySubscribers = new DiyMultiMap<>();
		diySubscribers.put("topic.a", 1);
		diySubscribers.put("topic.a", 2);
		diySubscribers.put("topic.a", 3);
		diySubscribers.put("topic.a", 3);
		diySubscribers.put("topic.b", 1);
		System.out.println(diySubscribers.get("topic.a")); // [1, 2, 3]
		System.out.println(diySubscribers.get("topic.b")); // [1]
		System.out.println(diySubscribers.size()); // 4
	}

}
