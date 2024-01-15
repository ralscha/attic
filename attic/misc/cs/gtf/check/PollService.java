
package gtf.check;

import java.io.*;
import gtf.check.*;

import common.util.AppProperties;

public class PollService implements Runnable, gtf.common.Constants {
	private final static int DEFAULT_WAIT_TIME = 60;
	private Thread myThread;
	private int waitTimeInSeconds;

	public PollService() {
		this(AppProperties.getIntegerProperty(P_POLLING_WAIT_SECONDS, DEFAULT_WAIT_TIME));
	}

	public PollService(int waitTimeInSeconds) {
		this.waitTimeInSeconds = waitTimeInSeconds;
		myThread = new Thread(this);

		myThread.start();
	}

	public synchronized void join() throws InterruptedException {
		if (myThread != null) {
			myThread.join();
		} 
	} 

	public static void main(String args[]) {
		new PollService();
	} 

	public void run() {
		while (!myThread.isInterrupted()) {
			String okFileName = 
				AppProperties.getStringProperty(P_POLLING_TRANSFER_OK_FILE);
			File transferOKFile = new File(okFileName);

			if (transferOKFile.exists()) {
				transferOKFile.delete();

				new AWZACheck().check();
				new LiabCheck().check477();
				new LiabCheck().check405();
				new LiabCheck().check406();
				new LiabCheck().check407();

				// Thread beenden
				return;
			} 

			try {
				myThread.sleep(waitTimeInSeconds * 1000);
			} catch (InterruptedException ex) {}
		} 
	} 

	public void stop() {
		myThread.interrupt();
	} 

}