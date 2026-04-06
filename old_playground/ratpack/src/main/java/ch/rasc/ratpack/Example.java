package ch.rasc.ratpack;

import java.util.HashMap;
import java.util.Map;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

public class Example implements Handler {
	@Override
	public void handle(final Context context) {
		Map<String, Object> test = new HashMap<>();
		test.put("one", 1);
		test.put("two", 2);
		context.render(Jackson.json(test));
		// context.getResponse().send("Hello world from the Example handler!");
	}
}