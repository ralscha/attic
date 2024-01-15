package test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.TimeZone;
import common.log.Log;


/**
 * Testing for the log system.  This will eventually be broken out
 * into individual tests in the test directory.
 **/
public class GeneralTest extends Base {
    private int numThreads = 1;
	
    public static void main(String[] args) {
	int numThreads = 1;
	
	if (args.length > 0) {
	    numThreads = Integer.parseInt(args[0]);
	}
	
	GeneralTest test = new GeneralTest(numThreads);
	test.run();
    }

    public static void testFunction() {
	Log.trace();
    }

    public static void testThrowFunction()
	throws java.io.IOException
    {
	throw new java.io.IOException("Exception message");
    }

    public static void testNullPointerException()
    {
	throw new java.lang.NullPointerException();
    }

    public GeneralTest(int numThreads) {
	this.numThreads = numThreads;
    }
	
    public void run() {

	for (int i = 0; i < numThreads; ++i) {
	    new Thread("Test-Thread." + i) {
		public void run() {
		    logSomeMessages();
		}
	    }.start();
	}

	ThreadGroup group = Thread.currentThread().getThreadGroup();
	Thread[] threads = new Thread[group.activeCount()];
	group.enumerate(threads);

	for (int i = 0; i < threads.length; ++i) {
	    if (threads[i] != null
		&& threads[i] != Thread.currentThread())
	    {
		try { threads[i].join(); }
		catch (Exception e) {}
	    }
	}

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
	Log.trace("plain-object", objects);

	objects.addElement(objects);
	Log.trace("recursive-object", objects);

	TestObject object = new TestObject();
	object.v = objects;
	Log.trace("another-recursive-object", object);

	Log.warning("Example of warning(message)");
	Log.error("Example of error(message)");

	try { testThrowFunction(); }
	catch (Exception e) { Log.error(e); }

	try { testNullPointerException(); }
	catch (Exception e) { Log.error(e); }

	Log.log("stats", "Example of different log event type");

	Log.log("notice", "Example of event handling", "test object");
	Log.notice("Example of event handling", "test object");

	logRemoteObject();

	Log.trace("done");
    }

    public static class TestRemoteClass implements java.rmi.Remote {
	public int getValue1() { return 111; }
	public String getValue2() { return "222"; }
	public float getValue3() { return 333.333f; }
    }
	
    /**
     * This logs a remote object to test if this works.  
     **/
    void logRemoteObject() {
	Log.notice("remoteObject", new TestRemoteClass());
    }
	    
	    
    public static class TestObject {
	//
	// The following are only for the Log/PrintWriter to inspect
	// to test the reflection.
	//
	private int i = 123;
	private double d = 123.45;
	private String s = "a sample string";
	private String nullString = null;
	private Vector v = null;
	public Date date;	// test and date and public, init in ctor

	public TestObject() {
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTimeZone(TimeZone.getTimeZone("MST"));
	    calendar.set(1963, 1, 12, 20, 30, 1);
	    date = new Date(calendar.getTime().getTime());
	}
	
	public int getI() { return i; }
	public double getD() { return d; }
	public String getS() { return s; }
	public String getNullString() { return nullString; }
	public Vector getV() { return v; }
    }
}