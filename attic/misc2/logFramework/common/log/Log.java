package common.log;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.text.SimpleDateFormat;
import common.util.AppProperties;
import common.log.handler.Handler;

public class Log {
	
	private static final String HANDLER_PROPERTY_PREFIX = "log.handler.";
		
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    public static final String NOTICE = "notice";
    public static final String TRACE = "trace";
	
	private static Handler allHandlers = null;
	private static Map eventHandlers = Collections.synchronizedMap(new HashMap());

	private static Set excludeEventTypes = new HashSet();
	
	private static Throwable throwable = new Throwable();
	private static StackWriter writer = new StackWriter();
	private static LogEventFormat defaultObjectFormat = new LogEventFormat("%(%j)o");

	
	private static final SimpleDateFormat internalDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	static {
		initialize();
	}
	
	
	public static void enableErrors(boolean enabled) {
		if (enabled)
			excludeEventTypes.remove(ERROR);
		else
			excludeEventTypes.add(ERROR);
	}
	
	public static void enableWarnings(boolean enabled) {
		if (enabled)
			excludeEventTypes.remove(WARNING);
		else
			excludeEventTypes.add(WARNING);
	}
	
	public static void enableNotices(boolean enabled) {
		if (enabled)
			excludeEventTypes.remove(NOTICE);
		else
			excludeEventTypes.add(NOTICE);
	}
	
	public static void enableTraces(boolean enabled) {
		if (enabled)
			excludeEventTypes.remove(TRACE);
		else
			excludeEventTypes.add(TRACE);
	}
	
	public static void enableEventType(String eventType, boolean enabled) {
		if (enabled)
			excludeEventTypes.remove(eventType);
		else
			excludeEventTypes.add(eventType);		
	}

	public static void log(String eventType, String message) {
		if (!excludeEventTypes.contains(eventType)) 
			log(new LogEvent(eventType, message, new StackTrace(1)));
	}

	public static void log(String eventType, Object object) {
		if (!excludeEventTypes.contains(eventType)) 
			log(new LogEvent(eventType, null, new StackTrace(1), object));
	}

	public static void log(String eventType, String message, Object object) {
		if (!excludeEventTypes.contains(eventType)) 
			log(new LogEvent(eventType, message, new StackTrace(1), object));
	}

	
	public static void error(String message) {
		if (!excludeEventTypes.contains(ERROR)) 
			log(new LogEvent(ERROR, message, new StackTrace(1)));
	}
	
	public static void error(String message, Object object) {
		if (!excludeEventTypes.contains(ERROR)) 
			log(new LogEvent(ERROR, message, new StackTrace(1), object));
	}

	public static void error(Exception exception) {
		if (!excludeEventTypes.contains(ERROR)) 
			log(new LogEvent(ERROR, null, new StackTrace(exception), exception));
	}

	public static void error(String message, Exception exception) {
		if (!excludeEventTypes.contains(ERROR)) 
			log(new LogEvent(ERROR, message, new StackTrace(exception), exception));
	}

	public static void warning(String message) {
		if (!excludeEventTypes.contains(WARNING)) 
			log(new LogEvent(WARNING, message, new StackTrace(1)));
	}

	public static void warning(String message, Object object) {
		if (!excludeEventTypes.contains(WARNING)) 
			log(new LogEvent(WARNING, message, new StackTrace(1), object));
	}

	public static void warning(String message, Exception exception) {
		if (!excludeEventTypes.contains(WARNING)) 
			log(new LogEvent(WARNING, message, new StackTrace(exception), exception));
	}

	public static void notice(String message) {
		if (!excludeEventTypes.contains(NOTICE)) 
			log(new LogEvent(NOTICE, message, new StackTrace(1)));
	}

	public static void notice(String message, Object object) {
		if (!excludeEventTypes.contains(NOTICE)) 
			log(new LogEvent(NOTICE, message, new StackTrace(1), object));
	}

	public static void trace() {
		if (!excludeEventTypes.contains(TRACE)) 
			log(new LogEvent(TRACE, null, new StackTrace(1)));
	}

	public static void trace(String message) {
		if (!excludeEventTypes.contains(TRACE)) 
			log(new LogEvent(TRACE, message, new StackTrace(1)));
	}

	public static void trace(Object object) {
		if (!excludeEventTypes.contains(TRACE)) 
			log(new LogEvent(TRACE, null, new StackTrace(1), object));
	}

	public static void trace(String message, Object object) {
		if (!excludeEventTypes.contains(TRACE)) 
			log(new LogEvent(TRACE, message, new StackTrace(1), object));
	}

	protected static void log(LogEvent event) {
		Handler hs = (Handler)eventHandlers.get(event.getType());

		if (hs != null)
			hs.handle(event);

		if (allHandlers != null)
			allHandlers.handle(event);
	}

	public static void cleanUp() {	
		synchronized(eventHandlers) {  
			Iterator i = eventHandlers.values().iterator(); 

			while(i.hasNext()) {
				Handler hs = (Handler)i.next();
				hs.cleanUp();
			}
		}
		
		if (allHandlers != null)
			allHandlers.cleanUp();
	}
		

	public static void addHandler(Handler handler) {
		addHandler(handler, null);
	}
	
	public static void addHandler(Handler handler, String events) {
		if ((events == null) || (events.trim().equals(""))) {
			allHandlers = LogMulticaster.add(allHandlers, handler);
		} else {
			StringTokenizer tokenizer = new StringTokenizer(events);
			while(tokenizer.hasMoreTokens()) {
				String event = tokenizer.nextToken();
				eventHandlers.put(event, LogMulticaster.add((Handler)eventHandlers.get(event), handler));
			}
		}
	}	
	
	protected static void addHandler(String name) {
		String handlerClassName = AppProperties.getStringProperty(HANDLER_PROPERTY_PREFIX + name + ".class");
		String events = AppProperties.getStringProperty(HANDLER_PROPERTY_PREFIX + name + ".events", "");
		
		Class handlerClass = null;
		try {
			handlerClass = Class.forName(handlerClassName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (handlerClass != null) {		
			try {
				Class types[] = {String.class, String.class};
				Constructor constructor = handlerClass.getConstructor(types);
				Object params[] = {HANDLER_PROPERTY_PREFIX, name};
				Handler handler = (Handler)constructor.newInstance(params);
				addHandler(handler, events);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	protected static void initialize() {

		Enumeration properties = AppProperties.getInstance().propertyNames();
		while(properties.hasMoreElements()) {
			String key = (String)properties.nextElement();
			
			if (key.startsWith(HANDLER_PROPERTY_PREFIX) &&
					key.endsWith(".class")) {
				int begin = HANDLER_PROPERTY_PREFIX.length();
				int end = key.indexOf(".", begin);
				if (end != -1) {
					addHandler(key.substring(begin, end));
				}
			}
		}

		/*
		if (!addedAtLeastOneHandler)
			addHandler(new StandardOutHandler(), null);
		*/
		initializeEnables();
	}

	protected static void initializeEnables() {
		boolean allEnabled = AppProperties.getBooleanProperty("log", true);
		
		if (!AppProperties.getBooleanProperty("log.errors", allEnabled))
			excludeEventTypes.add(ERROR);
		
		if (!AppProperties.getBooleanProperty("log.warnings", allEnabled))
			excludeEventTypes.add(WARNING);
		
		if (!AppProperties.getBooleanProperty("log.notices", allEnabled))
			excludeEventTypes.add(NOTICE);
		
		if (!AppProperties.getBooleanProperty("log.traces", allEnabled))
			excludeEventTypes.add(TRACE);
	}
	
	public static synchronized void internal(String message) {
		System.out.print(internalDateFormat.format(new java.util.Date()));
		StackTrace position = new StackTrace(1);
		System.out.print(": ");
		System.out.print(position.getShortClassname());
		System.out.print('.');
		System.out.print(position.getFunction());
		System.out.print(':');
		System.out.print(position.getLineNumber());
		System.out.print(": internal: ");
		System.out.println(message);
	}
	
	public static synchronized void internal(Exception e) {
		System.out.print(internalDateFormat.format(new java.util.Date()));
		StackTrace position = new StackTrace(1);
		System.out.print(": ");
		System.out.print(position.getShortClassname());
		System.out.print('.');
		System.out.print(position.getFunction());
		System.out.print(':');
		System.out.print(position.getLineNumber());
		System.out.print(": internal: ");
		e.printStackTrace();
		System.out.println();
	}


}