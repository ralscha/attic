package common.util.log;

import java.text.*;
import java.util.*;

public abstract class Logger {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS"); 
	
	public abstract void writeLogEntry(String logEntry);
	
	public void cleanUp() {
	}
	
	protected String getTimeString() {
		Date today = new Date();
		return dateFormat.format(today);
	}
	
	
	
}