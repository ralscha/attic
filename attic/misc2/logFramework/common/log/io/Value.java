package common.log.io;

public class Value {
	public String name;
	public Object object;
	public Class clazz;
	public boolean isFirst = false;
	public boolean isLast = false;
	public boolean isSubObject = false;

	public Value(String name, Object object, boolean isSubObject) {
		this.name = name;
		this.object = object;
		this.clazz = (object == null) ? Object.class : object.getClass();
		this.isSubObject = isSubObject;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

	public Class getObjectClass() {
		return clazz;
	}
	public void setObjectClass(Class clazz) {
		this.clazz = clazz;
	}

	public boolean isFirst() {
		return isFirst;
	}
	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}
	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isSubObject() {
		return isSubObject;
	}
	public void setIsSubObject(boolean isSubObject) {
		this.isSubObject = isSubObject;
	}

	public boolean isPrimitive() {
		return isPrimitive(true);
	}

	public boolean isPrimitive(boolean includeWrappers) {
		return clazz.isPrimitive() || clazz == String.class || (includeWrappers &&
        		(clazz == Integer.class || clazz == Long.class || clazz == Byte.class ||
         		clazz == Boolean.class || clazz == Float.class || clazz == Double.class ||
         		clazz == Character.class));
	}

	public void printTo(ObjectPrintWriter writer) {
		writer.printField(this);
	}

	public String toString() {
		return object.toString();
	}
}