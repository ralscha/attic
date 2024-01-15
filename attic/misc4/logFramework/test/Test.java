package test;

import java.io.*;
import java.util.*;
import common.util.AppProperties;
import common.log.*;

public class Test {

	public void testFunction() {
		Log.trace();
	}

	public void testThrowFunction() throws IOException {
		throw new IOException("Exception message");
	}

	public void testNullPointerException() {
		throw new NullPointerException();
	}

	public void run() {
		for (int i = 0; i < 2; i++)
			(new Thread("Test-Thread." + i) {

 			public void run() {
 				logSomeMessages();
 			}

 		}
		).start();

		ThreadGroup group = Thread.currentThread().getThreadGroup();
		Thread threads[] = new Thread[group.activeCount()];
		group.enumerate(threads);
		for (int i = 0; i < threads.length; i++)
			if (threads[i] != null && threads[i] != Thread.currentThread())
				try {
					threads[i].join();
				} catch (Exception _ex) { }

		Log.notice("done");
	}

	public void logSomeMessages() {
		Log.notice("Examples of trace()");
		Log.trace();
		testFunction();
		Log.trace("Example of trace(message)");
		Vector objects = new Vector();
		objects.addElement("Value1");
		objects.addElement("Value2");
		Log.trace("Example of trace(message, object)", objects);
		Log.trace("Another Example of trace(message, object)", new Test(objects));
		Log.warning("Example of warning(message)");
		Log.error("Example of error(message)");
		try {
			testThrowFunction();
		} catch (Exception e) {
			Log.error(e);
		}
		try {
			testNullPointerException();
		} catch (Exception e) {
			Log.error(e);
		}
		Log.log("stats", "Example of different log Event type");
		Log.log("notice", "Example of Event handling", "test object");
		Log.notice("Example of Event handling", "test object");
		Log.trace("done");
	}

	public int getI() {
		return i;
	}

	public double getD() {
		return d;
	}

	public String getS() {
		return s;
	}

	public String getNullString() {
		return nullString;
	}

	public Vector getV() {
		return v;
	}

	private int i;
	private double d;
	private String s;
	private String nullString;
	private Vector v;

	public Test() {
		i = 123;
		d = 123.45D;
		s = "a sample string";
	}

	Test(Vector v) {
		i = 123;
		d = 123.45D;
		s = "a sample string";
		this.v = v;
	}
	
	public static void main(String args[]) {
		
		//AppProperties.putStringProperty("log.traces", "false");
		/*
		AppProperties.putStringProperty("log.handler.file1.class", "common.log.handler.SplitFileHandler");
		AppProperties.putStringProperty("log.handler.file1.events", "error warning");
		AppProperties.putStringProperty("log.handler.file1.path",  "'d:\\download\\file1.log'");

		
		AppProperties.putStringProperty("log.handler.file2.class", "common.log.handler.SplitFileHandler");
		AppProperties.putStringProperty("log.handler.file2.events", "notice trace");
		AppProperties.putStringProperty("log.handler.file2.path",  "'d:\\download\\file2.log'");

		AppProperties.putStringProperty("log.handler.file3.class", "common.log.handler.SplitFileHandler");
		AppProperties.putStringProperty("log.handler.file3.path",  "'d:\\download\\fileAll.log'");		

		AppProperties.putStringProperty("log.handler.file4.class", "common.log.handler.SplitFileHandler");
		AppProperties.putStringProperty("log.handler.file4.events", "notice error");
		AppProperties.putStringProperty("log.handler.file4.path",  "'d:\\download\\file3.log'");		
		*/
		AppProperties.putStringProperty("log.handler.out.class", "common.log.handler.StandardOutHandler");

		/*
		AppProperties.putStringProperty("log.handler.mail.class", "common.log.handler.SMTPHandler");
		AppProperties.putStringProperty("log.handler.mail.events", "error");
		AppProperties.putStringProperty("log.handler.mail.sender", "rschaer@datacomm.ch");
		AppProperties.putStringProperty("log.handler.mail.receiver", "rschaer@datacomm.ch");
		AppProperties.putStringProperty("log.handler.mail.subject", "Log");
		AppProperties.putStringProperty("log.handler.mail.smtp", "smtp.datacomm.ch");
		*/	

		Test test = new Test();
		test.logSomeMessages();
	}	
	

}	
	

	
	