package common.log.io;

import java.io.*;

public class JavaPrintWriter extends ObjectPrintWriter {

	private int indent = 0;
	private static final String indentString = "    ";

	public JavaPrintWriter(OutputStream stream) {
		super(stream);
	}
	
	public JavaPrintWriter(OutputStream stream, boolean autoFlush) {
		super(stream, autoFlush);
	}

	public JavaPrintWriter(Writer writer) {
		super(writer);
	}
	
	public JavaPrintWriter(Writer writer, boolean autoFlush) {
		super(writer, autoFlush);
	}


	protected void indent() {
		for (int i = 0; i < indent; ++i)
			print(indentString);
	}

	public void printField(Value value) {
		if (value.object == null)
			printNullObject();
		else {
			printType(value, false, false);

			separateTypeAndName();
			printName(value.name);
			separateNameAndValue();
			printValue(value);
			print(";");
			separateObjectItems();
		}
	}

	protected void newlineAndIndent() {
		print("\n");
		indent();
	}

	public void beginList() {
		print("{");
		++indent;
		if (prettyOutput())
			newlineAndIndent();
	}

	public void endList() {
		if (--indent < 0)
			indent = 0;
		if (prettyOutput())
			newlineAndIndent();
		print("}");
	}

	public void beginObject() {
		beginList();
	}

	public void endObject() {
		endList();
	}

	protected void separateObjectItems() {
		if (prettyOutput())
			newlineAndIndent();
		else
			print(" ");
	}

	protected void separateTypeAndName() {
		print(" ");
	}

	protected void separateNameAndValue() {
		if (prettyOutput())
			print(" = ");
		else
			print("=");
	}

	protected void printNullString() {
		print("null;");
	}
	protected void printNullObject() {
		print("null;");
	}


}