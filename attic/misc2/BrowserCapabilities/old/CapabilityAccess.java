
import java.util.*;
import java.lang.reflect.*;

public class CapabilityAccess {

	private Map methodMap;

	
	public CapabilityAccess() {
		
		Class clazz = Capability.class;
	
		methodMap = new HashMap();
	
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {		
			if (methods[i].getName().startsWith("set")) {
				Class[] params = methods[i].getParameterTypes();
				methodMap.put(methods[i].getName(), new MethodEntry(methods[i], params[0]));
			}	
		}		
		
	}

	public void setValue(Object target, String field, String value) {
		field = "set"+firstCharToUpperCase(field);
		MethodEntry entry = (MethodEntry)methodMap.get(field);
		
		if (entry != null) {
			Method method = entry.getMethod();
			Class param = entry.getParam();
			
			Object[] parameters = new Object[1];
			
			if (param == String.class) {
				parameters[0] = value;
			} else {
				if (param.isPrimitive()) {
					if (param == boolean.class) {
						parameters[0] = new Boolean(value);
					} else if (param == int.class) {
						parameters[0] = new Integer(value);
					}
				}
			}	
			
						
			try {
				method.invoke(target, parameters);
			} catch (Exception e) {
				System.err.println(e);
			}
			
		} else {
			System.out.println("no param for : " + field);
		}
	}
	
	
	public String firstCharToUpperCase(String str) {
		if ((str != null) && (str.length() > 0)) {
			StringBuffer sb = new StringBuffer(str.length());
			sb.append(Character.toUpperCase(str.charAt(0)));
			
			if (str.length() < 4) 
				sb.append(str.substring(1).toUpperCase());
			else 
				sb.append(str.substring(1).toLowerCase());
				
			return sb.toString();			
		} else {
			return str;
		}
	}
	
	public static void main(String[] args) {
		try {
			CapabilityAccess ca = new CapabilityAccess();
			Capability c = new Capability("TEST");
			ca.setValue(c, "platform", "soso");
			System.out.println("OK");
			System.out.println(c.getPlatform());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private class MethodEntry {
		private Method method;
		private Class parameter;
		
		MethodEntry(Method m, Class param) {
			method = m;
			parameter = param;
		}
		
		Method getMethod() {
			return method;
		}
		
		Class getParam() {
			return parameter;
		}
	}

}