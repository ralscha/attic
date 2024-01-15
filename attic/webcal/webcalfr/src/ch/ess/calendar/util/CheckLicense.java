
package ch.ess.calendar.util;


public class CheckLicense {
		
	private final static String unlimited = "ch.ess.calendar.util.License";
	private static License license = null;
	
	
	static {
		Class foundClass = null;
				
			try {
				foundClass = Class.forName(unlimited);
			} catch (ClassNotFoundException cnfe) { }
		
		if (foundClass == null) {
			license = null;
		} else {
		  try {
		  license = (License)foundClass.newInstance();
		  } catch (Exception ie) {} 
		}
	}

	public static boolean isDemo() {
		return license == null;
	}

	public static int getLimit() {
		if (license != null)
			return license.getLimit();
		else
			return -1;	
	}

	public static void main(String[] args) {
		System.out.println(CheckLicense.getLimit());
	}
}