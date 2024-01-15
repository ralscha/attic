package common.util.log;

import java.io.*;
import java.util.*;
import java.text.*;

public class DailyDiskLogger extends AsyncDiskLogger {

	//TO DO
	//Programme die länger als einen Tag laufen Woche ununterbrochen laufen
	//check in writeLogEntry ob neuer Tag

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");    
	
	public DailyDiskLogger(String logPath) {
		super(logPath);		
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