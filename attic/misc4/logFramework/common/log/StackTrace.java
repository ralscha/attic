//0.7
package common.log;

public class StackTrace implements java.io.Serializable {
	private static StackWriter writer = new StackWriter();

	private Throwable stackTrace;
	private Throwable exception;

	private String classname = "";
	private String function = "";
	private String filename = "";
	private int lineNumber = -1;
	private int currentLevel = 0;


	public synchronized static String currentStackLine(Throwable throwable, int level) {
		writer.clear();
		writer.setDesiredLineNum(level);
		throwable.printStackTrace(writer);
		return writer.toString();
	}


	public StackTrace() {
		stackTrace = new Throwable();
		setLevel(0);
	}

	public StackTrace(int numLevelsUp) {
		stackTrace = new Throwable();
		setLevel(numLevelsUp);
	}

	public StackTrace(Exception exception) {
		stackTrace = new Throwable();
		this.exception = exception;
		setLevel(-1);
	}


	public StackTrace(Exception exception, int numLevelsUp) {
		stackTrace = new Throwable();
		this.exception = exception;
		setLevel(numLevelsUp - 1);
	}


	/**
	  * Sets the desired depth of in a stack trace that properly
	  * indicates the callers level in the stack trace.
	  **/
	public void setLevel(int numLevelsUp) {
		currentLevel = numLevelsUp + 2;
		Throwable throwable = (exception == null) ? stackTrace : exception;

		String line = currentStackLine(throwable, currentLevel);
		if (line.equals("")) {
			line = currentStackLine(stackTrace, 1);
		}

		parse(line);
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getClassname() {
		return classname;
	}
	public String getShortClassname() {
		try {
			return classname.substring(classname.lastIndexOf('.') + 1, classname.length());
		} catch (Exception e) {
			return classname;
		}
	}

	public String getFunction() {
		return function;
	}
	public String getFilename() {
		return filename;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	public String getLineNumberString() {
		return (lineNumber >= 0) ? "" + lineNumber : "";
	}


	/**
	  * Breaks the given line into a class name, line number, function,
	  * etc.  Presumably, the line corresponds to a line from a stack
	  * trace.
	  **/
	protected void parse(String raw) {
		//
		// Throughout this function, error conditions such as
		// expected delimiters that are not present is handled
		// by catching the generated indexing exceptions on
		// the string class rather than handling every error.
		//

		lineNumber = 0;
		filename = "";
		function = "";
		classname = "";

		int startLineNumber = 0;
		try {
			//
			// First, start at the end of the string and search
			// backwards to capture the line number.
			//
			startLineNumber = raw.lastIndexOf(':');
			String lineNumberString = raw.substring(startLineNumber + 1, raw.length() - 1);
			lineNumber = Integer.valueOf(lineNumberString).intValue();
		} catch (Exception e) {}

		int startFilename = 0;
		try {
			//
			// Continue backward, for the filename.
			//
			startFilename = raw.lastIndexOf('(');
			if (startLineNumber <= 0) {
				startLineNumber = raw.indexOf(')', startFilename);
				if (startLineNumber <= 0)
					startLineNumber = raw.length() - 1;
			}

			filename = raw.substring(startFilename + 1, startLineNumber);
		} catch (Exception e) {}

		int startFunction = 0;
		try {
			//
			// Continue backward for the function name.
			//
			startFunction = raw.lastIndexOf('.', startFilename);
			function = raw.substring(startFunction + 1, startFilename);
		} catch (Exception e) {}

		try {
			//
			// Continue backward for the class name.
			//
			int startClassname = raw.lastIndexOf(' ', startFunction);

			// Fix if we ran off the front of the string.
			if (startClassname == -1)
				startClassname = 0;
			else
				++startClassname;

			classname = raw.substring(startClassname, startFunction);
		} catch (Exception e) {
		}
	}


	public String toString() {
		return "class=" + getClassname() + " function=" + getFunction() + " file=" +
       		getFilename() + " line=" + getLineNumberString();
	}
}