package common.util.log;

public final class Log {
	
	private static Logger logger = new ScreenLogger();
	
	public static void setLogger(Logger l) {
		logger = l;
	}
	
	public static void log(String logEntry) {
		logger.writeLogEntry(logEntry);
	}
	
	public static void cleanUp() {
		logger.cleanUp();
	}
		
}