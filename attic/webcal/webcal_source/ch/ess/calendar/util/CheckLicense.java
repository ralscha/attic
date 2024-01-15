
package ch.ess.calendar.util;

import java.io.*;
import java.security.*;

public class CheckLicense {
	
	private final static String small     = "SmallLicense";
	private final static String unlimited = "UnlimitedLicense";
	private static License license = null;
	
	
	static {
		Class foundClass = null;
		try {
			foundClass = Class.forName(small);
		} catch (ClassNotFoundException cnfe) { }
		
		if (foundClass == null) {
			try {
				foundClass = Class.forName(unlimited);
			} catch (ClassNotFoundException cnfe) { }
		}	
		
		if (foundClass == null) {
			license = null;
		} else {
			try {
				license = (License)foundClass.newInstance();
				
				InputStream mdis = license.getClass().getResourceAsStream("dat");
				InputStream oris = license.getClass().getResourceAsStream(license.getClass().getName()+".class");
								
				MessageDigest md = MessageDigest.getInstance("MD5");
						
				byte[] buffer = new byte[256];
         	while (true) {
            	int bytesRead = oris.read(buffer);	
	            if (bytesRead == -1) break;
					md.update(buffer, 0, bytesRead);
      	   }
				oris.close();			
				String result1 = new String(md.digest());
				
				int bytesRead = mdis.read(buffer);
				String result2 = new String(buffer, 0, bytesRead);
				
				if (!result1.equals(result2)) {
					license = null;
				}
				
			} catch (Exception e) {
				System.err.println(e);
				license = null;
			}
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