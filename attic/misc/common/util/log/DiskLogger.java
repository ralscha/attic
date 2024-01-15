package common.util.log;

import java.io.*;
import java.util.*;

public abstract class DiskLogger extends Logger {

	protected String logPath;
	private PrintWriter logWriter;
		
	public DiskLogger(String logPath) {
		this.logPath = logPath.trim();
				
		try {
			this.logWriter = new PrintWriter(new FileWriter(createLogFileName(), true), true);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		
	}
	
	public synchronized void writeLogEntry(String logEntry) {
		logWriter.print(getTimeString());
		logWriter.print('|');
		logWriter.println(logEntry);		
	}
	
	public synchronized void cleanUp() {
		closeLogWriter();
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