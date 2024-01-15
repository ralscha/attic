package test;
import common.log.Log;
import common.util.*;

public class ObjectSelfReferenceTest extends Base {
	private int sampleInt = 1;
	private String sampleString = "a string";
	private Object sampleObject = this;

	public void run() {
		Log.trace("this", this);
	}

	public int getSampleInt() {
		return sampleInt;
	}
	public String getSampleString() {
		return sampleString;
	}
	public Object getSampleObject() {
		return sampleObject;
	}

	public String toString() {
		return "" + sampleInt + " " + sampleString + " ";
	}

	public static void main(String[] args) {
		AppProperties.putStringProperty("log.handler.out.class", "common.log.handler.StandardOutHandler");
		new ObjectSelfReferenceTest().run();
	}
}