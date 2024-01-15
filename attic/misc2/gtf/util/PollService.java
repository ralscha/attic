package gtf.util;

import java.io.*;
import gtf.control.*;
import common.util.*;

public class PollService implements Runnable {

	private final static int DEFAULT_WAIT_TIME = 60;
	private Thread myThread;
	private int waitTimeInSeconds;	

	
	public PollService() {
		this(AppProperties.getIntProperty("gtf.polling.wait.seconds"));
	}
	
	public PollService(int waitTimeInSeconds) {
		this.waitTimeInSeconds = waitTimeInSeconds;		
		myThread = new Thread(this);
		myThread.start();
	}
	
	public synchronized void join() throws InterruptedException {
		if (myThread != null)
			myThread.join();
	}
	
	public static void main(String args[]) {				
		new PollService();
	}
	
	public void run() {
		
		while (!myThread.isInterrupted()) {
			
			String okFileName = AppProperties.getStringProperty("gtf.transfer.ok.file");
			File transferOKFile = new File(okFileName);

			if (transferOKFile.exists()) {
				transferOKFile.delete();
				
				String bookingsFileName = AppProperties.getStringProperty("awza.check.bookings.file");
				new AWZACheck().check(bookingsFileName);
				
				new LiabCheck().check477();
				new LiabCheck().check405();
				new LiabCheck().check406();
				new LiabCheck().check407();
				
				//Thread beenden
				return; 
			}
	
			try {
				myThread.sleep(waitTimeInSeconds * 1000);
			} catch (InterruptedException ex) {	}
		}
	}
	
	public void stop() {
		myThread.interrupt();
	}
}