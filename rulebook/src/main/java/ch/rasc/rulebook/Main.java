package ch.rasc.rulebook;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;

public class Main {

	public static void main(String[] args) {
		// https://dzone.com/articles/rulebook-a-simple-rules-engine-that-leverages-java
		// https://dzone.com/articles/rulebook-grows-up-keeping-feature-rich-software-simple

		RuleBook ruleBook = RuleBookBuilder.create()
				.addRule(rule -> rule.withFactType(String.class)
						.when(f -> f.containsKey("hello")).using("hello")
						.then(System.out::print))
				.addRule(rule -> rule.withFactType(String.class)
						.when(f -> f.containsKey("world")).using("world")
						.then(System.out::println))
				.build();

		NameValueReferableMap factMap = new FactMap();
		factMap.setValue("hello", "Hello ");
		factMap.setValue("world", " World");
		ruleBook.run(factMap);
	}

}
