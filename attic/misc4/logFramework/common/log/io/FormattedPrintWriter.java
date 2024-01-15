package common.log.io;

import java.io.*;

public class FormattedPrintWriter extends ObjectPrintWriter {

	private String headerFormat = "";
	private String beginBlockFormat = "{";
	private String fieldFormat = "%c %n=%v;";
	private String endBlockFormat = " }";
	private boolean firstCall = true;


	public FormattedPrintWriter(OutputStream stream) {
		super(stream);
	}
	public FormattedPrintWriter(OutputStream stream, boolean autoFlush) {
		super(stream, autoFlush);
	}

	public FormattedPrintWriter(Writer writer) {
		super(writer);
	}
	public FormattedPrintWriter(Writer writer, boolean autoFlush) {
		super(writer, autoFlush);
	}


	/**
	  * Uses the bar character to separate the beginBlock, field, and
	  * endBlock formats.
	  **/
	public void setFormat(String format) {
		setFieldFormat("invalid");
		setBeginBlockFormat("invalid");
		setHeaderFormat("invalid");

		// Header
		int bar = format.indexOf('|');
		if (bar != -1) {
			setHeaderFormat(format.substring(0, bar));

			// Begin block
			int start = bar + 1;
			bar = format.indexOf('|', start);
			if (bar != -1) {
				setBeginBlockFormat(format.substring(start, bar));

				// Field
				start = bar + 1;
				bar = format.indexOf('|', start);
				if (bar != -1) {
					setFieldFormat(format.substring(start, bar));

					// End block
					if (bar + 1 < format.length()) {
						setEndBlockFormat(format.substring(bar + 1));
					}
				}
			}
		}
	}


	/**
	  * Sets the 4 parts of the format.  See the description of this
	  * class above for more information.
	  **/
	public void setFormat(String header, String beginBlock, String field, String endBlock) {
		setHeaderFormat(header);
		setBeginBlockFormat(beginBlock);
		setFieldFormat(field);
		setEndBlockFormat(endBlock);
	}

	public void setHeaderFormat(String format) {
		this.headerFormat = format;
	}
	public void setBeginBlockFormat(String format) {
		this.beginBlockFormat = format;
	}
	public void setFieldFormat(String format) {
		this.fieldFormat = format;
	}
	public void setEndBlockFormat(String format) {
		this.endBlockFormat = format;
	}


	protected void printField(Value value) {
		if (firstCall) {
			firstCall = false;
			print(headerFormat);
		}

		boolean inCommand = false;
		boolean skipNextIfLast = false;
		for (int i = 0; i < fieldFormat.length(); ++i) {
			char c = fieldFormat.charAt(i);

			if (inCommand) {
				inCommand = false;
				switch (c) {
					case '%':
						print(c);
						break;

					case 'i': // append the indent
						indent();
						break;

					case 'C': // append the class name
						printType(value, false, false);
						break;

					case 'c': // append the class name
						printType(value, true, true);
						break;

					case 't': // append the class name
						printType(value, true, false);
						break;

					case 'n': // append the name
						printName(value.name);
						break;

					case 'v': // append the value
						if (value == null)
							printNullObject();
						else
							printValue(value);
						break;

					case 'r': // append the value
						print(referenceNameOf(value));
						break;

					case 'z': // append the value
						skipNextIfLast = true;
						break;

					default:
						break;
				}
			} else if (c == '%') {
				inCommand = true;
			} else {
				if (skipNextIfLast && value.isLast) {
					skipNextIfLast = false;
				} else
					print(c);
			}
		}
	}


	protected void printBlocking(String format) {
		boolean inCommand = false;
		for (int i = 0; i < format.length(); ++i) {
			char c = format.charAt(i);

			if (inCommand) {
				inCommand = false;
				switch (c) {
					case '%':
						print(c);
						break;

					case 'i': // append the indent
						indent();
						break;

					default:
						break;
				}
			} else if (c == '%') {
				inCommand = true;
			} else {
				print(c);
			}
		}
	}


	public void printBeginBlock() {
		printBlocking(beginBlockFormat);
	}

	public void printEndBlock() {
		printBlocking(endBlockFormat);
	}


	protected void indent() {
		for (int i = 0; i < indent; ++i)
			print(indentString);
	}

	protected void newlineAndIndent() {
		print("\n");
		indent();
	}

	public void beginList() {
		printBeginBlock();
		++indent;
	}

	public void endList() {
		if (--indent < 0)
			indent = 0;
		printEndBlock();
	}

	public void beginObject() {
		beginList();
	}

	public void endObject() {
		endList();
	}

	public void separateListItems() {
	}
	public void separateObjectItems() {
	}

	protected void printNullString() {
		print("null");
	}
	protected void printNullObject() {
		print("null");
	}

	private int indent = 0;
	private static final String indentString = "    ";
}