package common.log;

import java.io.*;
import java.text.*;
import java.util.*;
import common.util.AppProperties;

public class LogEventFormat {
	private static Properties properties = new Properties();
	
	// Relative time format
	private static String defaultRelativeTimeFormat = AppProperties.getStringProperty("log.time.relative", "clock");

	// Time format
	private static DateFormat defaultTimeFormat;
	static {
		defaultTimeFormat = getDateFormat(AppProperties.getStringProperty("log.time.format", "24"));
		defaultTimeFormat.setTimeZone(TimeZone.getTimeZone(AppProperties.getStringProperty("log.time.zone", "CET")));
	}

	// Object time format
	private static DateFormat defaultObjectTimeFormat;
	static {
		defaultObjectTimeFormat = getDateFormat(AppProperties.getStringProperty("log.object.time.format", "24"));
		defaultObjectTimeFormat.setTimeZone(
  		TimeZone.getTimeZone(AppProperties.getStringProperty("log.object.time.zone", "CET")));
	}

	// Exception format
	private static String defaultExceptionFormat =
		expandSpecialChars(AppProperties.getStringProperty("log.exception.format", ": %s"));

	// Message format
	private static String defaultMessageFormat =
		expandSpecialChars(AppProperties.getStringProperty("log.message.format", "%m"));

	// Object format
	private static String terseJavaFormat = ":|{| %c %n = %v;| }";
	private static String prettyJavaFormat = ":|{\n|%i%c %n = %v;\n|%i}";
	private static String lispFormat = ":|(%v%z,%z |)";
	private static String defaultObjectFormat =
		expandSpecialChars(AppProperties.getStringProperty("log.object.format", "java"));

	// Thread format
	private static String defaultThreadFormat =
		expandSpecialChars(AppProperties.getStringProperty("log.thread.format", "%G.%n"));

	// General Format
	private static String defaultFormat = expandSpecialChars(AppProperties.getStringProperty("log.format", "%t(%r):%n: %e: %h: %c.%f:%l%(: %m)m%o\\n\\n"));
	

	private static boolean useColors;
	static {
		useColors = AppProperties.getBooleanProperty("log.color", false);
		useColors = AppProperties.getBooleanProperty("log.colour", useColors);
		useColors = AppProperties.getBooleanProperty("log.colors", useColors);
		useColors = AppProperties.getBooleanProperty("log.colours", useColors);
	}


	public static final String NO_COLOR = "";
	public static final char ESC = '\u001B';
	public static final String PLAIN = ESC + "[00m";
	public static final String BRIGHT = ESC + "[1m";
	public static final String UNDERLINE = ESC + "[4m";
	public static final String HIGHLIGHT = ESC + "[7m";
	public static final String BLACK = ESC + "[30m";
	public static final String RED = ESC + "[31m";
	public static final String GREEN = ESC + "[32m";
	public static final String YELLOW = ESC + "[33m";
	public static final String BLUE = ESC + "[34m";
	public static final String PURPLE = ESC + "[35m";
	public static final String CYAN = ESC + "[36m";
	public static final String WHITE = ESC + "[37m";
	public static final String BLACK_BACKGROUND = ESC + "[40m";
	public static final String RED_BACKGROUND = ESC + "[41m";
	public static final String GREEN_BACKGROUND = ESC + "[42m";
	public static final String YELLOW_BACKGROUND = ESC + "[43m";
	public static final String BLUE_BACKGROUND = ESC + "[44m";
	public static final String PURPLE_BACKGROUND = ESC + "[45m";
	public static final String CYAN_BACKGROUND = ESC + "[46m";
	public static final String WHITE_BACKGROUND = ESC + "[47m";

	private static Map colorTable = new HashMap();
	static {
		colorTable.put("plain", PLAIN);
		colorTable.put("bright", BRIGHT);
		colorTable.put("underline", UNDERLINE);
		colorTable.put("highlight", HIGHLIGHT);
		colorTable.put("black", BLACK);
		colorTable.put("red", RED);
		colorTable.put("green", GREEN);
		colorTable.put("yellow", YELLOW);
		colorTable.put("blue", BLUE);
		colorTable.put("purple", PURPLE);
		colorTable.put("cyan", CYAN);
		colorTable.put("white", WHITE);
		colorTable.put("black-bg", BLACK_BACKGROUND);
		colorTable.put("red-bg", RED_BACKGROUND);
		colorTable.put("green-bg", GREEN_BACKGROUND);
		colorTable.put("yellow-bg", YELLOW_BACKGROUND);
		colorTable.put("blue-bg", BLUE_BACKGROUND);
		colorTable.put("purple-bg", PURPLE_BACKGROUND);
		colorTable.put("cyan-bg", CYAN_BACKGROUND);
		colorTable.put("white-bg", WHITE_BACKGROUND);
	}


	private static final Date startTime = new Date(); // used for relative
	private static final NumberFormat numberFormat = NumberFormat.getInstance();
	private static final NumberFormat twoDigitFormat = new DecimalFormat("00");



	private String format;
	private int minBufferSize = 128;

	private DateFormat timeFormat = defaultTimeFormat;
	private String relativeTimeFormat = defaultRelativeTimeFormat;
	private String exceptionFormat = defaultExceptionFormat;
	private String messageFormat = defaultMessageFormat;
	private String objectFormat = defaultObjectFormat;
	private DateFormat objectTimeFormat = defaultObjectTimeFormat;
	private String threadFormat = defaultThreadFormat;

	private String timeColor = NO_COLOR;
	private String relativeTimeColor = NO_COLOR;
	private String exceptionColor = NO_COLOR;
	private String messageColor = NO_COLOR;
	private String objectColor = NO_COLOR;
	private String threadColor = NO_COLOR;
	private String classnameColor = NO_COLOR;
	private String functionColor = NO_COLOR;

	private String resetColor = NO_COLOR;
	private Map logEventColors = new HashMap();
	private Map lineColors = new HashMap();

	public LogEventFormat() {
		this(defaultFormat);
	}

	public LogEventFormat(String format) {
		this.format = format;
		useColors(useColors);
	}


	private StringBuffer createBuffer() {
		return minBufferSize == 0 ? new StringBuffer() : new StringBuffer(minBufferSize);
	}


	public String format(LogEvent event) {
		StringBuffer buffer = createBuffer();
		String subFormat = null;

		String lineColor = (String)lineColors.get(event.type);
		if (lineColor != null)
			buffer.append(lineColor);

		boolean inCommand = false;
		for (int i = 0; i < format.length(); ++i) {
			char c = format.charAt(i);

			if (inCommand) {
				if (lineColor != null)
					buffer.append(lineColor);

				inCommand = false;
				switch (c) {
					case '%':
						buffer.append(c);
						break;

					case 't': // Time
						formatTime(buffer, subFormat, event.time);
						break;

					case 'e': // LogEvent type
						String color = (String) logEventColors.get(event.type);
						if (color != null)
							buffer.append(color);
						buffer.append(event.type);
						buffer.append(resetColor);
						break;

					case 'r': // relative time (short)
					case 'R': // Relative Time (long)
						formatRelativeTime(buffer, subFormat, event.time);
						break;

					case 'h': // Thread name (short)
					case 'H': // Thread name (long)
						formatThreadName(buffer, subFormat, event.threadNames);
						break;

					case 'n': // Number
						buffer.append(event.number);
						break;

					case 'c': // Classname (short)
					case 'C': // Classname (long)
						buffer.append(classnameColor);
						buffer.append(event.position.getShortClassname());
						buffer.append(resetColor);
						break;

					case 'F': // File name
						buffer.append(event.position.getFilename());
						break;

					case 'f': // Function name
						buffer.append(functionColor);
						buffer.append(event.position.getFunction());
						buffer.append(resetColor);
						break;

					case 'l': // Line number
						buffer.append(event.position.getLineNumberString());
						break;

					case 'm': // Message (short)
					case 'M': // Message (long)
						//
						// Only print the message if there was no object
						// specified in this event.
						//
						if (event.message != null && !event.hasObject())
							formatMessage(buffer, subFormat, event.message);
						break;

					case 'o': // Object (short)
					case 'O': // Object (long)
						if (event.hasObject())
							formatObject(buffer, subFormat,
             							(event.message == null ? "" : event.message),
             							event.object);
						break;

					case '(': // start sub format
						{

							StringBuffer subBuffer = new StringBuffer();
							boolean escapeSeen = false;

							while (++i < format.length()) {
								char cc = format.charAt(i);

								if (cc == '\\')
									escapeSeen = true;
								else if (cc == ')' && !escapeSeen)
									break;
								else {
									subBuffer.append(cc);
									escapeSeen = false;
								}
							}

							subFormat = subBuffer.toString();
						}
						inCommand = true;
						break;

					default:
						break;
				}

				if (lineColor != null)
					buffer.append(lineColor);
			} else if (c == '%') {
				inCommand = true;
				subFormat = null;
			} else {
				buffer.append(c);
			}
		}

		//
		// To optimize the creation of the buffer, this next few lines
		// simply remembers the buffer length so it can use it the
		// next time this format is used.
		//
		if (minBufferSize < buffer.length()) {
			minBufferSize = buffer.length() + 16;
		}

		buffer.append(resetColor);
		return buffer.toString();
	}


	protected void formatException(StringBuffer buffer, Exception exception) {
		String subFormat = exceptionFormat;

		buffer.append(exceptionColor);

		boolean inCommand = false;
		for (int i = 0; i < subFormat.length(); ++i) {
			char c = subFormat.charAt(i);

			if (inCommand) {
				inCommand = false;
				switch (c) {
					case '%':
						buffer.append(c);
						break;

					case 'c': // append the class name
						buffer.append(exception.getClass().getName());
						break;

					case 'm': // append the exception message
						if (exception.getMessage() != null)
							buffer.append(exception.getMessage());
						break;

					case 's': // append the stack trace
						{
							StringWriter stringWriter = new StringWriter();
							PrintWriter writer = new PrintWriter(stringWriter);
							exception.printStackTrace(writer);
							buffer.append(eliminateEndingNewlines(stringWriter.toString()));
							writer.close();
						}
						break;

					default:
						break;
				}
			} else if (c == '%') {
				inCommand = true;
			} else {
				buffer.append(c);
			}
		}

		buffer.append(resetColor);
	}


	protected String eliminateEndingNewlines(String source) {
		int end = source.length();
		while (end > 0 && source.charAt(end - 1) == '\n')
			--end;

		return (end > 0) ? source.substring(0, end) : source;
	}


	protected void formatMessage(StringBuffer buffer, String subFormat, String message) {
		if (messageFormat == defaultMessageFormat) {
			if (subFormat == null)
				subFormat = messageFormat;
		} else
			subFormat = messageFormat;

		buffer.append(messageColor);

		boolean inCommand = false;
		for (int i = 0; i < subFormat.length(); ++i) {
			char c = subFormat.charAt(i);

			if (inCommand) {
				inCommand = false;
				switch (c) {
					case '%':
						buffer.append(c);
						break;

					case 'm': // append the message itself
						buffer.append(message);
						break;

					default:
						break;
				}
			} else if (c == '%') {
				inCommand = true;
			} else {
				buffer.append(c);
			}
		}

		buffer.append(resetColor);
	}



	protected void formatFormattedObject(String subFormat, StringBuffer buffer,
                                     	String name, Object object) {
		StringWriter stringWriter = new StringWriter();
		common.log.io.FormattedPrintWriter writer = new common.log.io.FormattedPrintWriter(stringWriter, false);
		writer.setDateFormat(objectTimeFormat);
		writer.setFormat(subFormat);

		writer.print(name, object);
		writer.close();
		try {
			stringWriter.close();
		} catch (Exception e) {
			Log.internal(e);
		}

		buffer.append(stringWriter.toString());
	}


	protected void formatGenericObject(StringBuffer buffer, String subFormat, String name,
                                   	Object object) {
		if (objectFormat == defaultObjectFormat) {
			if (subFormat == null)
				subFormat = objectFormat;
		} else
			subFormat = objectFormat;

		buffer.append(objectColor);

		if (subFormat.equals("java")) {
			formatFormattedObject(terseJavaFormat, buffer, name, object);
		} else if (subFormat.equals("Java")) {
			formatFormattedObject(prettyJavaFormat, buffer, name, object);
		} else if (subFormat.equals("lisp")) {
			formatFormattedObject(lispFormat, buffer, name, object);
		} else if (subFormat.equals("Lisp")) {
			formatFormattedObject(lispFormat, buffer, name, object);
		} else {
			formatFormattedObject(subFormat, buffer, name, object);
		}

		buffer.append(resetColor);
	}



	/**
	  * This formats a string representation of the given object.  It
	  * uses one of the grace.io.PrintWriters to convert the object
	  * to a string.
	  *
	  * @param buffer to which to write object
	  * @param format from which to format this object
	  * @param message to use as value's name (should never be null)
	  * @param object object to format
	  **/
	protected void formatObject(StringBuffer buffer, String format, String message,
                            	Object object) {
		if (object instanceof Throwable) {
			formatException(buffer, (Exception) object);
		} else {
			formatGenericObject(buffer, format, message, object);
		}
	}



	protected void formatThreadName(StringBuffer buffer, String subFormat, List names) {
		if (threadFormat == defaultThreadFormat) {
			if (subFormat == null)
				subFormat = threadFormat;
		} else
			subFormat = threadFormat;

		buffer.append(threadColor);

		char groupDelimiter = '.';
		boolean inCommand = false;
		for (int i = 0; i < subFormat.length(); ++i) {
			char c = subFormat.charAt(i);

			if (inCommand) {
				inCommand = false;
				switch (c) {
					case '%':
						buffer.append(c);
						break;

					case 'n': // append the thread name only
						if (names.size() >= 1) {
							buffer.append((String) names.get(names.size() - 1));
						}
						break;

					case 'g': // append thread's group name
						if (names.size() >= 2) {
							buffer.append((String) names.get(names.size() - 2));
						}
						break;

					case 'G': // append all of the thread's group names
						for (int j = 0; j < names.size() - 1; ++j) {
							if (j != 0)
								buffer.append(groupDelimiter);
							buffer.append((String) names.get(j));
						}
						break;

					default: // set the delimiter separates the group names
						groupDelimiter = c;
						break;
				}
			} else if (c == '%') {
				inCommand = true;
			} else {
				buffer.append(c);
			}
		}

		buffer.append(resetColor);
	}


	protected void formatRelativeTime(StringBuffer buffer, String subFormat, Date now) {
		if (relativeTimeFormat == defaultRelativeTimeFormat) {
			if (subFormat == null)
				subFormat = relativeTimeFormat;
		} else
			subFormat = relativeTimeFormat;

		buffer.append(relativeTimeColor);

		if (subFormat != null && !subFormat.equals("")) {
			double difference = (double)(now.getTime() - startTime.getTime());
			if (subFormat.startsWith("clock")) {
				int days = (int)(difference / 86400000);
				int hours = (int)((difference % 86400000) / 3600000);
				int minutes = (int)((difference % 3600000) / 60000);
				double seconds = (difference % 60000) / 1000.0;

				if (days > 0) {
					buffer.append(twoDigitFormat.format(days));
					buffer.append(":");
					buffer.append(twoDigitFormat.format(hours));
					buffer.append(":");
					buffer.append(twoDigitFormat.format(minutes));
					buffer.append(":");
					buffer.append(numberFormat.format(seconds));
				} else if (hours > 0) {
					buffer.append(twoDigitFormat.format(hours));
					buffer.append(":");
					buffer.append(twoDigitFormat.format(minutes));
					buffer.append(":");
					buffer.append(numberFormat.format(seconds));
				} else if (minutes > 0) {
					buffer.append(twoDigitFormat.format(minutes));
					buffer.append(":");
					buffer.append(numberFormat.format(seconds));
				} else {
					buffer.append(numberFormat.format(seconds));
				}
			} else if (subFormat.startsWith("day")) {
				difference /= 86400000.0;
				buffer.append(numberFormat.format(difference));
			} else if (subFormat.startsWith("min")) {
				difference /= 60000.0;
				buffer.append(numberFormat.format(difference));
			} else if (subFormat.startsWith("sec")) {
				difference /= 1000.0;
				buffer.append(numberFormat.format(difference));
			} else { // default to relative hours
				difference /= 3600000.0;
				buffer.append(numberFormat.format(difference));
			}
		}
		buffer.append(resetColor);
	}


	protected void formatTime(StringBuffer buffer, String subFormat, Date now) {
		DateFormat toUse = timeFormat;

		if (timeFormat == defaultTimeFormat && subFormat != null) {
			toUse = getDateFormat(subFormat);
		}
		buffer.append(timeColor);
		buffer.append(toUse.format(now));
		buffer.append(resetColor);
	}




	/**
	  * Utility function used by <code>formatTime(...)</code> to find
	  * and return java.text.DateFormat for the given name.
	  *
	  * @param name { short, medium, long, full, <SimpleDateFormat> }
	  *
	  * @return DateFormat corresponding to name
	  **/
	protected static DateFormat getDateFormat(String name) {
		DateFormat format = null;

		if (name.equals("short")) {
			format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		} else if (name.equals("medium")) {
			format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		} else if (name.equals("long")) {
			format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		} else if (name.equals("full")) {
			format = java.text.DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		} else if (name.equals("24")) {
			format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		} else {
			// assume it is a SimpleDateFormat pattern
			format = new SimpleDateFormat(name);
		}

		return format;
	}


	protected static String expandSpecialChars(String string) {		
		if (string != null) {
			StringBuffer buffer = new StringBuffer(string.length());

			boolean inEscape = false;
			for (int i = 0; i < string.length(); ++i) {
				char c = string.charAt(i);

				if (inEscape) {
					inEscape = false;
					switch (c) {
						case 'n':
							buffer.append('\n');
							break;
						case 't':
							buffer.append('\t');
							break;
						default:
							buffer.append(c);
							break;
					}
				} else if (c == '\\')
					inEscape = true;
				else {
					buffer.append(c);
				}
			}

			return buffer.toString();
		}

		return null;
	}

	public void setObjectFormat(String format) {
		objectFormat = format;
	}
	public void setObjectTimeFormat(DateFormat format) {
		objectTimeFormat = format;
	}
	public void setTimeFormat(DateFormat format) {
		timeFormat = format;
	}
	public void setRelativeTimeFormat(String format) {
		relativeTimeFormat = format;
	}
	public void setExceptionFormat(String format) {
		exceptionFormat = format;
	}
	public void setMessageFormat(String format) {
		messageFormat = format;
	}
	public void setThreadFormat(String format) {
		threadFormat = format;
	}

	public void useColors(boolean colors) {
		if (colors) {
			timeColor = BRIGHT;
			resetColor = PLAIN;
			logEventColors.put(Log.ERROR, HIGHLIGHT + RED);
			logEventColors.put(Log.WARNING, HIGHLIGHT);
			lineColors.put(Log.ERROR, BRIGHT + RED);
			lineColors.put(Log.WARNING, BRIGHT);
		} else {
			timeColor = NO_COLOR;
			resetColor = NO_COLOR;
			logEventColors.clear();
		}
	}

	public void setTimeColor(String color) {
		timeColor = color;
		resetColor = PLAIN;
	}

	public void setRelativeTimeColor(String color) {
		relativeTimeColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted exception. **/
	public void setExceptionColor(String color) {
		exceptionColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted message. **/
	public void setMessageColor(String color) {
		messageColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted object. **/
	public void setObjectColor(String color) {
		objectColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted thread. **/
	public void setThreadColor(String color) {
		threadColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted classname. **/
	public void setClassnameColor(String color) {
		classnameColor = color;
		resetColor = PLAIN;
	}

	/** Sets the color of the formatted function. **/
	public void setFunctionColor(String color) {
		functionColor = color;
		resetColor = PLAIN;
	}

	/**
	  * Sets color used to print the given LogEvent string in each log
	  * line.  This means if the given LogEvent type is logged, the text
	  * of the LogEvent type will be colored the given color.
	  *
	  * @param LogEvent to print in color
	  * @param color that can be a concatenation of constant colors in
	  * this class.
	  **/
	public void setLogEventColor(String eventType, String color) {
		logEventColors.put(eventType, color);
		resetColor = PLAIN;
	}

	public void setLineColor(String event, String color) {
		lineColors.put(event, color);
		resetColor = PLAIN;
	}
}