package common.log;

import java.io.*;
import java.util.*;

public class WeeklyDiskLogger extends DiskLogger {

	//TO DO
	//Programme die länger als eine Woche ununterbrochen laufen
	//
	//private int currentWeek;
	
	public WeeklyDiskLogger(String logPath) {
		super(logPath);
	}
		
	protected String createLogFileName() {
		Calendar today = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		
		sb.append(logPath);
		
		if (!logPath.endsWith(File.separator))
			sb.append(File.separator);
		
		sb.append("week");
			
		sb.append(today.get(Calendar.YEAR));		
	
		int weekno = today.get(Calendar.WEEK_OF_YEAR);
		if (weekno < 10)
			sb.append("0");
	
		sb.append(weekno);
		
		sb.append(".log");
		
		return (sb.toString());
	}

}