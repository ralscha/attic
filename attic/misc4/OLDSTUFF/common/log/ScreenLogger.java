package common.log;

public class ScreenLogger extends Logger {
	
	public synchronized void writeLogEntry(String logEntry) {
		System.out.print(getTimeString());
		System.out.print('|');
		System.out.println(logEntry);
	}
	
}