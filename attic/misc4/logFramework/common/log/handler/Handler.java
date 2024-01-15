package common.log.handler;

import common.log.LogEvent;

public abstract class Handler {
	public abstract void handle(LogEvent event);
	
	public void cleanUp() {}
}