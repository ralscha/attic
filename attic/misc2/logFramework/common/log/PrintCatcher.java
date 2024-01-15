package common.log;

import java.io.*;

public class PrintCatcher extends PrintStream {
	private String eventType = Log.NOTICE;

	public PrintCatcher() {
		super(System.out);
	}

	public PrintCatcher(String eventType) {
		this(eventType, System.out);
	}

	public PrintCatcher(String eventType, PrintStream stream) {
		super(stream);
	}

	public PrintCatcher(boolean allErrors) {
		super(System.out);
		if (allErrors)
			eventType = Log.ERROR;
	}

	public void print(String message) {
		LogEvent event = new LogEvent(eventType, message, new StackTrace(1));
		Log.log(event);
	}

	public void println(String message) {
		LogEvent event = new LogEvent(eventType, message, new StackTrace(1));
		Log.log(event);
	}

	public void print(Object object) {
		LogEvent event = new LogEvent(eventType, null, new StackTrace(1), object);
		Log.log(event);
	}

	public void print(Exception exception) {
		LogEvent event = new LogEvent(Log.ERROR, null, new StackTrace(exception), exception);
		Log.log(event);		

	}
}