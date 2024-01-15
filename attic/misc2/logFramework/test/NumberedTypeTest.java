package test;

import common.log.Log;
import common.util.AppProperties;

public class NumberedTypeTest extends Base {
	public static void main(String[] args) {
		AppProperties.putStringProperty("log.handler.out.class", "common.log.handler.StandardOutHandler");		
		new NumberedTypeTest().run();
	}

	public void run() {
		for (int i = 0; i < 100; ++i) {
			Log.log("", "" + i);
		}
	}
}