package lotto.util;

import java.io.*;
import java.util.*;
import java.text.*;

public class DiskLog {

    private static final DiskLog instance = new DiskLog();
   
   	public static final char DEBUG = 'D';
	public static final char INFO = 'I';
	public static final char WARNING = 'W';
	public static final char ERROR = 'E';
	public static final char FATAL = 'F';

    private static final int MAX_NUMBER_OF_LINES = 10000;

    private int numberOfLines;
    private String logName;
    private PrintWriter logWriter = null;
    private SimpleDateFormat dateFormat_file = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");    
    private SimpleDateFormat dateFormat_log  = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");    
    
    private StringBuffer logLine;

    public static DiskLog getInstance() {
        return instance;
    }

    
	public DiskLog() {
	    numberOfLines = 0;
	    this.logName = logName;
	    this.logLine = new StringBuffer();	   
	    
	    try {
    		logWriter = new PrintWriter(new FileWriter(createLogFileName(),true), true);
    	} catch (IOException ioe) {
    	    System.out.println(ioe);
    	}
	}

	private String createLogFileName() {
	    Calendar cal = new GregorianCalendar();   
		return(AppProperties.getInstance().getProperty("logPath").trim()+File.separator+dateFormat_file.format(cal.getTime()) + ".log");
	}

    public void close() {
        logWriter.close();
        logWriter = null;
    }

	public void log(char logLevel, String className, String logEntry) {
	    
	    if (logWriter == null) return;
	    
	    logLine.setLength(0);
	    
	    Calendar cal = new GregorianCalendar();
        	    
		logLine.append(dateFormat_log.format(cal.getTime()) + "|");
		logLine.append(logLevel + "|");
		logLine.append(className + "|");
		logLine.append(logEntry);

		try {
			logWriter.println(logLine.toString());
			numberOfLines++;
			
			if (numberOfLines >= MAX_NUMBER_OF_LINES) {
			    logWriter.close();
        		logWriter = new PrintWriter(new FileWriter(createLogFileName(),true), true);
                numberOfLines = 0;        		
			}
		} catch ( IOException e ) {
		    System.out.println(e);
		}
	}
}