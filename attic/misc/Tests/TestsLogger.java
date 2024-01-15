import common.util.log.*;
import java.util.*;

public class TestsLogger {

	private Random rnd = new Random();
	private final static int NUMBER_OF_THREADS = 50;

	public TestsLogger() {
		Log.setLogger(new WeeklyDiskLogger("d:\\log", true));
		
		long start = System.currentTimeMillis();
		
		
		Thread[] ta = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ta[i] = new LoggerThread(i);
			ta[i].start();
		}
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			try {
				ta[i].join();
			} catch (InterruptedException ie) { }
		}
		
		/*
		for (int i = 0; i < 10000; i++) {
			Log.log("Line : " + i);
		}
		*/
		System.out.println("time : " + (System.currentTimeMillis()- start));
		
		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException ie) { }
		Log.cleanUp();
		*/
	}

	class LoggerThread extends Thread {
	
		int no;
		
		LoggerThread(int no) {
			this.no = no;
		}
	
		public void run() {
			
			for (int i = 0; i < 10; i++) {
				Log.log("Thread " + no);
				
				/*
				try {
					Thread.sleep(rnd.nextInt(5000)+500);
				} catch (InterruptedException ie) {
					System.err.println(ie);
				}
				*/
			}
		}
	}

	public static void main(String args[]) {
		new TestsLogger();
	}
} 