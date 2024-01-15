import java.util.Hashtable;
import java.util.Enumeration;

public class Type implements java.io.Serializable, Cloneable {
	private int value;
	private transient String desc;

	// a hashtable of hashtables...
	private static final Hashtable types = new Hashtable();

	protected Type(int value, String desc) {
		this.value = value;
		this.desc = desc;
		checkForDuplicates(this);
		storeType(this);
	}

	private void checkForDuplicates(Type type) {
		String className = type.getClass().getName();

		Hashtable values;

		values = (Hashtable) types.get(className);

		if (values != null) {
			if (values.get(new Integer(type.getValue())) != null) {
				System.out.println("No Dupes Allowed: " + className + "=" + type);
				throw(new RuntimeException());
			}
		}
	}

	private void storeType(Type type) {
		String className = type.getClass().getName();

		Hashtable values;

		synchronized (types)// avoid race condition for creating inner table
		{
			values = (Hashtable) types.get(className);

			if (values == null) {
				values = new Hashtable();
				types.put(className, values);
			}
		}

		values.put(new Integer(type.getValue()), type);
	}

	public static Type getByValue(Class classRef, int value) {
		Type type = null;

		String className = classRef.getName();

		Hashtable values = (Hashtable) types.get(className);

		if (values != null) {
			type = (Type) values.get(new Integer(value));
		}

		return(type);
	}

	public static Enumeration elements(Class classRef) {
		String className = classRef.getName();

		Hashtable values = (Hashtable) types.get(className);

		if (values != null) {
			return(values.elements());
		} else {
			return null;
		}
	}

	public static int getMaxValue(Class classRef) {
		int max = -1;

		Enumeration e = elements(classRef);

		while (e.hasMoreElements()) {
			Type type = (Type) e.nextElement();

			int tmp = type.getValue();

			if (tmp > max) {
				max = tmp;
			}
		}

		return(max);
	}

	public int getValue() {
		return value;
	}

	public boolean equals(Object obj) {
		if (! (obj instanceof Type)) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if ((this.getClass() == obj.getClass()) &&
    			(this.getValue() == ((Type) obj).getValue())) {
			return true;
		}

		return false;
	}
}