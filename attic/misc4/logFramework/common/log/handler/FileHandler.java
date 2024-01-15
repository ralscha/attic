package common.log.handler;


import java.text.*;
import java.util.*;
import java.io.*;
import common.util.AppProperties;
import common.log.*;

public class FileHandler extends Handler {

	protected LogEventFormat format;
	protected PrintWriter logWriter;
	protected File logFile;
	
	
	public FileHandler(String prefix, String name) {
		String propertyPrefix = prefix + name;
		
		String path = AppProperties.getStringProperty(propertyPrefix + ".path", name + ".log");		
		setLogWriter(path);
		format = new LogEventFormat();
	}
	
	public FileHandler(String path) {
		format = new LogEventFormat();
		setLogWriter(path);
	}
	
	protected String formatFilename(String filename) {
		SimpleDateFormat format = new SimpleDateFormat(filename);
		return format.format(new Date());
	}
	
	protected void setLogWriter(String path) {
		try {
			logFile = new File(formatFilename(path));
			logWriter = new PrintWriter(new FileWriter(logFile.getAbsolutePath(), true),false);
		} catch (IOException ex) {
			System.err.println(ex);
			logFile = null;
			logWriter = new PrintWriter(System.out);
		}
	}

	public synchronized void handle(LogEvent event) {
		logWriter.print(format.format(event));
		logWriter.flush();
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
	
	public void finalize() {		
		closeLogWriter();
	}
	
	
	public static void main(String args[]) {
		try { 
			//SimpleDateFormat
			//Log.addHandler(new FileHandler("'week'yyyyww'.log'"));
			Log.addHandler(new FileHandler("'test.log'"));
			//Log.addHandler(new FileHandler("yyyyMMdd HHmmssSSS"));
			
			for (int i = 0; i < 200; i++) {
				Log.log("test", "test message " + i + " to force file over 1k");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}