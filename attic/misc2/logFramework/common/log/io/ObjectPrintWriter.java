package common.log.io;

import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import java.sql.SQLException;

public abstract class ObjectPrintWriter extends PrintWriter {

	private boolean autoFlush = false;
	private boolean pretty = true;
	private NameMap printedObjects = new NameMap("{ref-", "}");
	private boolean printObjectsOnlyOnce = true;
	private boolean firstPrint = true;

	private static Map printHandlers = new HashMap();
	private static Map classFieldAccessors = new HashMap();

	private static final Class[] printMethodArguments = { ObjectPrintWriter.class };

	
	public ObjectPrintWriter(OutputStream stream) {
		super(stream, false);
		this.autoFlush = false;
	}

	public ObjectPrintWriter(OutputStream stream, boolean autoFlush) {
		super(stream, autoFlush);
		this.autoFlush = autoFlush;
	}


	public ObjectPrintWriter(Writer writer) {
		super(writer, false);
		this.autoFlush = false;
	}

	public ObjectPrintWriter(Writer writer, boolean autoFlush) {
		super(writer, autoFlush);
		this.autoFlush = autoFlush;
	}

	public void print(String name, Object object) {
		
		boolean first = firstPrint;
		Value value = new Value(name, object, !first);
		if (first) {
			value.isFirst = true;
			value.isLast = true;
		}
		firstPrint = false;
		printField(value);
		if (!first) {
			autoFlush();
			firstPrint = false;
		}
	}

	public void print(String name, int value) {
		print(name, new Integer(value));
	}

	public void print(String name, byte value) {
		print(name, new Byte(value));
	}

	public void print(String name, char value) {
		print(name, new Character(value));
	}

	public void print(String name, float value) {
		print(name, new Float(value));
	}

	public void print(String name, double value) {
		print(name, new Double(value));
	}

	public void print(String name, boolean value) {
		print(name, new Boolean(value));
	}

	protected abstract void printField(Value value);

	protected void printNullString() {
		printNullObject();
	}
	protected void printNullObject() {
		super.print("null");
	}

	protected void printQuoted(String string) {
		if (string == null)
			printNullString();
		else {
			beginString();
			super.print(string);
			endString();
		}
	}


	protected void printValue(Value value) {
		if (value.object == null) {
			printNullObject();
			return;
		}

		if (value.clazz.isArray()) {
			print((Object[]) value.object);
		} else if (value.clazz == String.class) {
			printQuoted((String) value.object);
		} else if (value.isPrimitive(true)) {
			super.print(value.toString());
		} else if (!alreadyPrinted(value.object)) {
			rememberPrinted(value.object);

			PrintHandler handler = getPrintHandler(value.clazz);

			if (handler != null) {
				handler.handle(this, value);
			} else {
				List accessors = fieldAccessors(value.clazz);
				if (accessors != null) {
					printWithFieldAccessors(value, accessors);
				} else
					printQuoted(value.toString());
			}
		} else {
			print(referenceNameOf(value.object));
		}
	}


	public void printWithFieldAccessors(Value parent, List accessors) {
		beginObject();
		for (int i = 0; i < accessors.size(); ++i) {
			FieldAccessor accessor = (FieldAccessor) accessors.get(i);
			Value child = new Value(accessor.name, accessor.get(parent.object), true);

			if (i == 0)
				child.isFirst = true;
			else if (i == accessors.size() - 1)
				child.isLast = true;

			printField(child);
		}
		endObject();
	}


	public static void addPrintHandler(Class type, PrintHandler handler) {
		printHandlers.put(type, handler);
	}

	protected static PrintHandler getPrintHandler(Class toFind) {
		PrintHandler handler = null;

		handler = (PrintHandler) printHandlers.get(toFind);

		if (handler == null) {
			//
			// See if there is a handler that will handle a baser type.
			//
			Iterator iter = printHandlers.keySet().iterator();
			while (iter.hasNext()) {
				Class clazz = (Class)iter.next();
				if (clazz.isAssignableFrom(toFind)) {
					return (PrintHandler)printHandlers.get(clazz);
				}
			}
		}

		//
		// See if there is a printTo method
		//


		if (handler == null)
			handler = getPrintToHandler(toFind);
		return handler;
	}

	protected static String shortenedTypeName(Class type, boolean shortenNonJDKTypes) {
		String name = type.getName();

		if (type == Integer.class)
			name = "int";
		else if (type == Float.class)
			name = "float";
		else if (type == Long.class)
			name = "long";
		else if (type == Byte.class)
			name = "byte";
		else if (type == Boolean.class)
			name = "boolean";
		else if (type == Character.class)
			name = "char";
		else if (type == Double.class)
			name = "double";
		else if (name.startsWith("java") || shortenNonJDKTypes) {
			name = rightmostName(name);
		} else
			name = type.getName();

		return name;
	}

	protected static String rightmostName(String name) {
		int lastDot = name.lastIndexOf('.');
		return lastDot != -1 ? name.substring(lastDot + 1) : name;
	}

	protected void printType(Value value, boolean shorten, boolean shortenAll) {
		String typeName;

		if (value.object == null) {
			typeName = "Object";
		} else {
			if (value.clazz.isArray()) {
				typeName = arrayTypeName(value.clazz, shorten, shortenAll);
			} else if (shorten) {
				typeName = shortenedTypeName(value.clazz, shortenAll);
			} else
				typeName = value.clazz.getName();
		}

		print(typeName);
	}

	protected static String arrayTypeName(Class type, boolean shorten, boolean shortenAll) {
		type = type.getComponentType();
		String name = (shorten ? shortenedTypeName(type, shortenAll) : type.getName());

		return name + "[]";
	}


	protected void printName(Object name) {
		print(name);
	}


	protected static Method getPrintMethod(Class objectClass) {
		try {
			return objectClass.getDeclaredMethod("printTo", printMethodArguments);
		} catch (Exception e) {
			return null;
		}
	}

	static class PrintMethodHandler implements PrintHandler {
		Method printMethod;
		public PrintMethodHandler(Method printMethod) {
			this.printMethod = printMethod;
		}
		public void handle(ObjectPrintWriter writer, Value value) {
			try {
				printMethod.invoke(value.object, new Object[]{ writer });
			} catch (IllegalAccessException e) {
				System.err.println(e);
			} catch (java.lang.reflect.InvocationTargetException e) {
				System.err.println(e);
			}
		}
	}


	protected static PrintHandler getPrintToHandler(Class clazz) {
		PrintHandler handler = null;
		Method printMethod = getPrintMethod(clazz);
		if (printMethod != null) {
			handler = new PrintMethodHandler(printMethod);
			addPrintHandler(clazz, handler);
		}

		return handler;
	}


	private static abstract class FieldAccessor {
		public String name;
		public FieldAccessor(String name) {
			this.name = name;
		}
		public abstract Object get(Object owner);
	}

	private static class DirectFieldAccessor extends FieldAccessor {
		Field field;
		public DirectFieldAccessor(String name, Field field) {
			super(name);
			this.field = field;
		}

		public Object get(Object owner) {
			try {
				return field.get(owner);
			} catch (IllegalAccessException e) {
				return "<illegal-access>";
			}
		}
	}

	private static class MethodFieldAccessor extends FieldAccessor {
		Method accessor;
		public MethodFieldAccessor(String name, Method accessor) {
			super(name);
			this.accessor = accessor;
		}

		public Object get(Object owner) {
			try {
				return accessor.invoke(owner, null);
			} catch (IllegalAccessException e) {
				return "<illegal-access>";
			}
			catch (java.lang.reflect.InvocationTargetException e) {
				return "<illegal-access>";
			}
		}
	}


	protected static List fieldAccessors(Class clazz) {
		List accessors = (List)classFieldAccessors.get(clazz);
		if (accessors == null)
			accessors = rememberAccessorsUsingFields(clazz);
		if (accessors.size() == 0) {
			accessors = rememberAccessorsUsingGetMethods(clazz);
		}

		return accessors;
	}

	protected static List rememberAccessorsUsingGetMethods(Class clazz) {
		List remembered = new ArrayList();
		classFieldAccessors.put(clazz, remembered);

		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; ++i) {
			String name = methods[i].getName();
			// lower case the name to get the "field" name
			String fieldName = Character.toLowerCase(name.charAt(3)) + name.substring(4);

			Class[] arguments = methods[i].getParameterTypes();
			Class returnType = methods[i].getReturnType();

			if (name.startsWith("get") && arguments.length == 0 &&
    				methods[i].getDeclaringClass() != Object.class) {
				remembered.add(new MethodFieldAccessor(fieldName, methods[i]));
			}
		}

		return remembered;
	}


	protected static List rememberAccessorsUsingFields(Class clazz) {
		List remembered = new ArrayList();
		classFieldAccessors.put(clazz, remembered);

		List fields = new ArrayList();
		Class inspectClass = clazz;
		while (inspectClass != Object.class) {
			fields = add(inspectClass.getDeclaredFields(), fields);
			inspectClass = inspectClass.getSuperclass();
		}

		for (int i = 0; i < fields.size(); ++i) {
			Field field = (Field) fields.get(i);
			if ((field.getModifiers() & Modifier.STATIC) == 0) {
				String fieldName = field.getName();

				//
				// Public or use a get function?
				//
				if ((field.getModifiers() & Modifier.PUBLIC) == 1) {
					remembered.add(new DirectFieldAccessor(fieldName, field));
				} else {
					//
					// Try to call the get method for the field.
					//
					String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) +
                    					fieldName.substring(1);

					try {
						Method getMethod = clazz.getMethod(methodName, null);
						remembered.add(new MethodFieldAccessor(fieldName, getMethod));
					} catch (java.lang.NoSuchMethodException e) {} // ignore
				}
			}
		}

		return remembered;
	}


	public void setDateFormat(java.text.DateFormat format) {
		dateFormat = format;
	}


	protected static List add(Object[] first, List second) {
		List result = new ArrayList(first.length + second.size());

		for (int i = 0; i < first.length; ++i)
			result.add(first[i]);
		for (int i = 0; i < second.size(); ++i)
			result.add(second.get(i));

		return result;
	}


	public boolean prettyOutput() {
		return pretty;
	}
	public void setPrettyOutput(boolean pretty) {
		this.pretty = pretty;
	}

	public boolean getPrintObjectsOnlyOnce() {
		return printObjectsOnlyOnce;
	}
	public void setPrintObjectsOnlyOnce(boolean value) {
		printObjectsOnlyOnce = value;
	}

	public abstract void beginList();
	public abstract void endList();

	public abstract void beginObject();
	public abstract void endObject();

	protected void beginString() {
		super.print("\"");
	}
	protected void endString() {
		super.print("\"");
	}


	/**
	     * This records the fact the given object has been printed in the
	     * PrintStream and should not be printed again, presumably.
	     **/
	protected void rememberPrinted(Object object) {
		if (printObjectsOnlyOnce)
			printedObjects.put(object);
	}


	protected String referenceNameOf(Object object) {
		return printedObjects.nameOf(object);
	}


	protected boolean alreadyPrinted(Object object) {
		if (printObjectsOnlyOnce)
			return printedObjects.contains(object);
		else
			return false;
	}

	protected void autoFlush() {
		if (autoFlush)
			flush();
	}


	protected void printListElementValue(Object value, int index, int numElements) {
		Value element = new Value("[" + index + "]", value, true);
		element.isFirst = index == 0;
		element.isLast = index == numElements - 1;

		printField(element);
	}


	protected void print(Object[] array) {
		beginList();
		for (int i = 0; i < array.length; ++i) {
			printListElementValue(array[i], i, array.length);
		}
		endList();
	}


	protected void print(Vector vector) {
		beginList();
		boolean appendedOne = false;
		for (int i = 0; i < vector.size(); ++i) {
			printListElementValue(vector.elementAt(i), i, vector.size());
		}
		endList();
	}


	protected void print(Hashtable table) {
		Enumeration keys = table.keys();
		Enumeration elements = table.elements();

		beginList();
		boolean first = true;
		while (keys.hasMoreElements() && elements.hasMoreElements()) {
			Value element =
  			new Value(keys.nextElement().toString(), elements.nextElement(), true);

			element.isFirst = first;
			element.isLast = !elements.hasMoreElements();
			first = false;

			printField(element);
		}
		endList();
	}



	protected void print(Enumeration elements) {
		beginList();
		int count = 0;
		boolean first = true;
		while (elements.hasMoreElements()) {
			Value element = new Value("[" + count++ + "]", elements.nextElement(), true);

			element.isFirst = first;
			element.isLast = !elements.hasMoreElements();
			first = false;

			printField(element);
		}
		endList();
	}



	/**
	     * Just a simple object to differentiate from a ResultSet.
	     **/
	private class ResultSetRow {
		public java.sql.ResultSet results;
		public java.sql.ResultSetMetaData meta;
		public int row = 0;
		public ResultSetRow(java.sql.ResultSet resultSet) {
			results = resultSet;
			try {
				meta = results.getMetaData();
			} catch (Exception e) {
				meta = null;
			}
		}
	}

	public void print(ResultSetRow row) {
		beginList();
		try {
			if (row.meta != null) {
				for (int i = 0; i < row.meta.getColumnCount(); ++i) {
					Value value = new Value(row.meta.getColumnName(i + 1),
                        					row.results.getObject(i + 1), true);

					value.isFirst = i == 0;
					value.isLast = i == row.meta.getColumnCount() - 1;

					printField(value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // ignore
		endList();
	}

	protected void print(java.sql.ResultSet resultSet) {
		beginList();
		int rowNum = 0;
		boolean first = true;
		try {
			boolean more = resultSet.next();
			while (more) {
				ResultSetRow row = new ResultSetRow(resultSet);
				Value value = new Value("[" + rowNum + "]", row, true);
				value.isFirst = first;
				more = resultSet.next();
				value.isLast = more;
				printField(value);

				++rowNum;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // ignore
		endList();
	}


	protected DateFormat dateFormat;
	{
		dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("CET"));
	}


	static {
		//
		// Date handler
		//
		addPrintHandler(java.util.Date.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.printQuoted(writer.dateFormat.format((Date) value.object));
                			}
                		}
               		);

		//
		// Vector handler
		//
		addPrintHandler(Vector.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.print((Vector) value.object);
                			}
                		}
               		);

		//
		// Hashtable handler
		//
		addPrintHandler(Hashtable.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.print((Hashtable) value.object);
                			}
                		}
               		);

		//
		// Collection handler
		//
		addPrintHandler(Collection.class, new PrintHandler() {
             			public void handle(ObjectPrintWriter writer, Value value) {
             				Collection collection = (Collection) value.object;
             				Iterator iter = collection.iterator();
             				writer.beginList();

             				boolean appendedOne = false;
             				int count = 0;
             				boolean first = true;

            				while (iter.hasNext()) {
                					Value element = new Value("[" + count + "]", iter.next(), true);

                					element.isFirst = first;
                					element.isLast = !iter.hasNext();
                					first = false;

                					element.printTo(writer);
                					++count;
                				}
                				writer.endList();
                			}
                		}
               		);



		//
		// Enumeration handler
		//
		addPrintHandler(Enumeration.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.print((Enumeration) value.object);
                			}
                		}
               		);
		//
		// ResultSet handler
		//
		addPrintHandler(java.sql.ResultSet.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.print((java.sql.ResultSet) value.object);
                			}
                		}
               		);
		//
		// ResultSetRow handler
		//
		addPrintHandler(ResultSetRow.class, new PrintHandler() {
                			public void handle(ObjectPrintWriter writer, Value value) {
                				writer.print((ResultSetRow) value.object);
                			}
                		}
               		);
	}
}