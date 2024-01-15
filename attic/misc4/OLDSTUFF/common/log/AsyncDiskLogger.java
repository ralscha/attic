package common.log;

import java.io.*;
import java.util.*;
import EDU.oswego.cs.dl.util.concurrent.QueuedExecutor;

public abstract class AsyncDiskLogger extends Logger {

	protected String logPath;
	private PrintWriter logWriter;
	private QueuedExecutor executor;
	
	public AsyncDiskLogger(String logPath) {
		this.logPath = logPath.trim();
		executor = new QueuedExecutor();
		
		try {
			this.logWriter = new PrintWriter(new FileWriter(createLogFileName(), true), true);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	private void writeLogEntryBackground(String logEntry) {
		logWriter.print(getTimeString());
		logWriter.print('|');
		logWriter.println(logEntry);		
	}
	
	public synchronized void writeLogEntry(final String logEntry) {
		try {
			executor.execute(new Runnable() {
					public void run() {
						writeLogEntryBackground(logEntry);	
					}});
		} catch (InterruptedException ie) {
		}
	}
	
	private void cleanUpBackground() {
		closeLogWriter();
		executor.getThread().interrupt();
	}

	public synchronized void cleanUp() {
		try {
			executor.execute(new Runnable() {
					public void run() {
						cleanUpBackground();	
					}});
		} catch (InterruptedException ie) {
		}
	}
	
	protected void closeLogWriter() {
		if (logWriter != null) {
			logWriter.close();	
			logWriter = null;
		}
	}
	
	protected void openNewWriter() {
		try {
			logWriter = new PrintWriter(new FileWriter(createLogFileName(), true), true);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	public void finalize() {		
		closeLogWriter();
	}
	
	protected abstract String createLogFileName() ;

}