package common.log.handler;


import java.io.*;
import java.util.Date;
import common.util.AppProperties;
import common.log.*;

public class SplitFileHandler extends FileHandler {

	private long maxBytes = 0;
	private long size = 0;
	
	public SplitFileHandler(String prefix, String name) {
		super(prefix, name);
	
		String propertyPrefix = prefix + name;
		
		String maxSizeString = AppProperties.getStringProperty(propertyPrefix + ".maxsize");
		if (maxSizeString != null) {
			maxSizeString.trim();
			maxSizeString = maxSizeString.toLowerCase();
			int multiplier = 1;
			if (maxSizeString.endsWith("k"))
				multiplier = 1000;
			else if (maxSizeString.endsWith("m"))
				multiplier = 0xf4240;
			try {
				maxBytes = Integer.parseInt(maxSizeString) * multiplier;
			} catch (Exception _ex) {
				maxBytes = 0L;
			}
		}
	}

	public SplitFileHandler(String filename, int maxKilobytes) {
		super(filename);
		maxBytes = maxKilobytes * 1024;
	}

	
	public synchronized void handle(LogEvent event) {
		String s = format.format(event);
		size += s.length();
		logWriter.print(s);
		logWriter.flush();
		
		if ((maxBytes > 0L) && (logFile != null) && (size > maxBytes))
			cutFileInHalf();
	}
	

	protected synchronized void cutFileInHalf() {
		if (logFile != null) {
			try {
				logWriter.close();
				BufferedReader reader = new BufferedReader(new FileReader(logFile));
				reader.skip(size - maxBytes / 2L);
				while (reader.read() != 10) ;
				
				File tempFile = File.createTempFile("log", null);
				tempFile.deleteOnExit();
				
				FileWriter tempWriter = new FileWriter(tempFile);
				String line;
				while ((line = reader.readLine()) != null) {
					tempWriter.write(line);
					tempWriter.write(10);
				}

				tempWriter.close();
				reader.close();
				logFile.delete();
				tempFile.renameTo(logFile);
				
			} catch (Exception _ex) {
				System.err.println(_ex);
			}
			setLogWriter(logFile.getName());
			size = logFile.length();
		}
	}

	public static void main(String args[]) {
		try {
			File log = new File("test.log");
			Log.addHandler(new SplitFileHandler(log.getName(), 10));
			for (int i = 0; i < 200; i++) {
				Log.log("test", "test message " + i + " to force file over 1k");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}