package common.log;

import java.io.*;
import java.util.*;
import java.text.*;

public class LineLimitDiskLogger extends DiskLogger {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");    
	private static final int DEFAULT_LINE_LIMIT = 10000;
	
	private int numberOfLines;

	private int lineLimit;
	
	public LineLimitDiskLogger(String logPath) {
		this(logPath, DEFAULT_LINE_LIMIT);
	}

	public LineLimitDiskLogger(String logPath, int lineLimit) {
		super(logPath);
		this.lineLimit = lineLimit;
		numberOfLines = 0;		
	}
		
	public synchronized void writeLogEntry(String logEntry) {
		super.writeLogEntry(logEntry);
		
		numberOfLines++;
		if (numberOfLines >= lineLimit) {
			closeLogWriter();
			openNewWriter();				
			numberOfLines = 0;
		}
	}
	
	protected String createLogFileName() {		
		Date now = new Date();
		
		StringBuffer sb = new StringBuffer();		
		sb.append(logPath);
		
		if (!logPath.endsWith(File.separator))
			sb.append(File.separator);
		
		sb.append(dateFormat.format(now));
		
		sb.append(".log");		
		return (sb.toString());
	}
	
}