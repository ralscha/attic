package common.log;

import java.io.*;
import java.util.*;

public class LogFileDeleteRunner implements Runnable {
	private String path = null;
/**
 * This method was created in VisualAge.
 * @param path java.lang.String
 */
public LogFileDeleteRunner(String path) {
	this.path = path;	
}
/**
 * run method comment.
 */
public void run() {
	if (path != null) {

		File pathFile = new File(path);
		String[] list = pathFile.list();
		if (list == null) return;
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE,-60);
		

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
			if (list[i].startsWith("week")) {
				if (refName.compareTo(list[i]) >= 1) {
					File tmpFile = new File(path, list[i]);
					tmpFile.delete();
				}
			}
		}
		
	}
}
}