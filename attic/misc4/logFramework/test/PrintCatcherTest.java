package test;

import common.log.PrintCatcher;
import common.util.AppProperties;

public class PrintCatcherTest extends Base {
	public static void main(String[] args) {
		
		AppProperties.putStringProperty("log.handler.out.class", "common.log.handler.StandardOutHandler");
		new PrintCatcherTest().run();
	}

	public void run() {
		System.setOut(new PrintCatcher());
		System.out.print("test message");
		System.out.print("another message");
	}
}