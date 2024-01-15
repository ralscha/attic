import java.io.PrintStream;
import java.util.*;

public class PhoneticList {

	private Hashtable basicH;


	public PhoneticList() {
		this(1000);
	}

	public PhoneticList(int i) {
		basicH = new Hashtable(i);
	}

	public String put(String s, Object obj) {
		String s1 = MetaPhone.metaPhone(s);
		if (basicH.containsKey(s1)) {
			Object obj1 = basicH.get(s1);
			if (obj1 instanceof Vector) {
				((Vector) obj1).addElement(obj);
			} else {
				Vector vector = new Vector();
				vector.addElement(obj1);
				vector.addElement(obj);
				basicH.put(s1, vector);
			}
		} else {
			basicH.put(s1, obj);
		}
		return s1;
	}

	public Object[] match(String s) {
		Object aobj[] = null;
		String s1 = MetaPhone.metaPhone(s);
		Object obj = basicH.get(s1);
		if (obj == null)
			return aobj;

		if (obj instanceof String) {
			aobj = new Object[1];
			aobj[0] = obj;
		} else {
			Vector vector = (Vector) obj;
			int i = vector.size();
			aobj = new Object[i];
			for (int j = 0; j < i; j++)
				aobj[j] = vector.elementAt(j);

		}
		return aobj;
	}

	public void dump(PrintStream printstream) {
		printstream.println("Basic size = " + basicH.size());
		for (Enumeration enumeration = basicH.keys(); enumeration.hasMoreElements();) {
			Object obj = enumeration.nextElement();
			Object obj1 = basicH.get(obj);
			printstream.print(obj + ": ");
			if (obj1 instanceof Vector) {
				Vector vector = (Vector) obj1;
				int i = vector.size() - 1;
				for (int j = 0; j < i; j++)
					printstream.print(vector.elementAt(j) + " / ");

				printstream.println(vector.lastElement());
			} else {
				printstream.println(obj1);
			}
		}

	}


}