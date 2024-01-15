package common.util.log;

import java.io.*;
import java.util.*;

public class WeeklyDiskLogger extends DiskLogger {

	//TO DO
	//Programme die länger als eine Woche ununterbrochen laufen
	//
	//private int currentWeek;
	
	public WeeklyDiskLogger(String logPath) {
		this(logPath, false);
	}
	
	public WeeklyDiskLogger(String logPath, boolean deleteRunner) {
		super(logPath);
		
		if (deleteRunner) {
			new DeleteRunnerThread().start();
		}
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
	
	
	private class DeleteRunnerThread extends Thread {

		public void run() {
			if (logPath != null) {
		
				File pathFile = new File(logPath);
				File[] list = pathFile.listFiles();
				if (list == null) return;
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DATE,-90);
				
				StringBuffer sb = new StringBuffer(10);
				sb.append("week");
				
				sb.append(today.get(Calendar.YEAR));		
				int weekno = today.get(Calendar.WEEK_OF_YEAR);
				if (weekno < 10)
					sb.append("0");	
				sb.append(weekno);
				
				sb.append(".log");
				
				String refName = sb.toString();
		
				for (int i = 0; i < list.length; i++) {			
					if (list[i].getName().startsWith("week")) {
						if (refName.compareTo(list[i].getName()) >= 1) {
							list[i].delete();
						}
					}
				}
				
			}
		}
	}

}