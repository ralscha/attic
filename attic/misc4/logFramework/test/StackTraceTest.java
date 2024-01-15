package test;
import common.log.StackTrace;

public class StackTraceTest extends Base {
	public void testThrow(Exception toThrow) throws Exception {
		throw toThrow;
	}


	public void callTestThrow(Exception toThrow) throws Exception {
		testThrow(toThrow);
	}


	public void run() {
		{
			StackTrace pos = new StackTrace();
			System.out.println(pos);
		}

		try {
			testThrow(new NullPointerException());
		} catch (Exception e) {
			StackTrace pos = new StackTrace(e);
			System.out.println(pos);
		}

		try {
			callTestThrow(new NullPointerException());
		} catch (Exception e) {
			StackTrace pos = new StackTrace(e);
			System.out.println(pos);
		}

		try {
			"".charAt(20);
		} catch (Exception e) {
			e.printStackTrace();
			StackTrace pos = new StackTrace(e, 1);
			System.out.println(pos);
		}

		try {
			testThrow(new java.rmi.RemoteException());
		} catch (Exception e) {
			e.printStackTrace();
			StackTrace pos = new StackTrace(e);
			System.out.println(pos);
		}

		try {
			callTestThrow(new java.rmi.RemoteException());
		} catch (Exception e) {
			StackTrace pos = new StackTrace(e);
			System.out.println(pos);
		}
	}

	public static void main(String [] args) {
		new StackTraceTest().run();
	}
}