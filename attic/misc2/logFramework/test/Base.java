package test;

import common.util.AppProperties;

public class Base {


	public Base() {
		AppProperties.putStringProperty("log.time.format", "");
		AppProperties.putStringProperty("log.time.relative", "");
	}

	public void check(String name, boolean passed) {
		if (passed)
			report("test " + name + " passed.");
		else
			report("test " + name + " failed.");
	}

	public void report(String message) {
		System.out.println(message);
	}
}