

import java.util.*;
import java.lang.reflect.*;

public class UniversalComparator implements Comparator, java.io.Serializable {

	private String method;
	private Object[] param;
	private Class[] paramType;
	private Comparator innerComparator;

	public UniversalComparator(String method) {
		this(method, null, null);
	}

	// constructor
	public UniversalComparator(String method, Object[] param, Comparator comparator) {
		setComparator(method, param, comparator);
	}
	public UniversalComparator(String method, Comparator comparator) {
		this(method, null, comparator);
	}

	public void setComparator(String method, Object[] param, Comparator comparator) {

		this.method = method;
		this.param = param;

		//  set parameterType
		if (param != null) {
			paramType = new Class[param.length];
			for (int i = 0; i < param.length; i++) {
				paramType[i] = param[i].getClass();
			}
		} else {
			paramType = null;
		}

		this.innerComparator = comparator;
	}

	public int compare(Object first, Object second) {
		int answer = 0;

		try {
			Class c = first.getClass();
			Method m = c.getDeclaredMethod(method, paramType);

			Object o1 = m.invoke(first, param); //  first.method(param)
			Object o2 = m.invoke(second, param);

			if (innerComparator != null) {
				answer = innerComparator.compare(o1, o2);
			} else {
				Class ci = o1.getClass();
				Object[] op = new Object[1];
				Class[] ct = new Class[1];
				op[0] = o2;
				ct[0] = o2.getClass();
				Method mi = ci.getDeclaredMethod("compareTo",ct);

				Integer i = (Integer) mi.invoke(o1, op);
				answer = i.intValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}
}




