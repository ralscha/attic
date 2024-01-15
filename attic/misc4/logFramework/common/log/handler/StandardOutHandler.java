package common.log.handler;

import java.io.PrintWriter;
import common.log.*;

public class StandardOutHandler extends Handler {

	private PrintWriter out;
	private LogEventFormat format;	
	
	public StandardOutHandler(String prefix, String name) {
		this();
	}

	public StandardOutHandler() {
		format = new LogEventFormat();
		out = new PrintWriter(System.out, false);
	}


	public synchronized void handle(LogEvent event) {
		out.print(format.format(event));
		out.flush();
	}

}