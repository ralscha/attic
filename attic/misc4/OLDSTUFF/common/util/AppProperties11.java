package common.util;

import java.util.*;
import java.io.*;
import java.text.*;

public final class AppProperties11 extends Properties {
	
	private static DecimalFormat df = new DecimalFormat("000");
	private static AppProperties11 instance;
	private static String fileName = null;
	private static String defaultFileName = "AppProperties.props";

	
	protected AppProperties11() {
		super();
		String prop = System.getProperty("props.file");
		if (prop != null)
			defaultFileName = prop;
		
		try {	
			
			if (fileName == null) 
				fileName = defaultFileName;

			File checkFile = new File(fileName);
			if (checkFile.exists()) {
				FileInputStream in = new FileInputStream(fileName);
				load(in);
				in.close();
			}
			
		} catch (IOException ioe) {
			System.err.println("Constructor AppProperties11 : " + ioe);
		}
	}

	public static void setFileName(String datFile) {
		fileName = datFile;
		//instance = new AppProperties11();
	}
	
	public static double getDoubleProperty(String key) throws NumberFormatException {
		return Double.parseDouble((String) getInstance().get(key));
	}
	
	public static int getIntProperty(String key) throws NumberFormatException {
		return Integer.parseInt((String) getInstance().get(key));
	}
	
	public static String getStringProperty(String key) {
		return (String) getInstance().get(key);
	}
	
	public static Vector getStringArrayProperty(String keyStr) {
		Vector list = new Vector();
				
		int i = 1;
		
		while(true) {
			String key = keyStr + '.' + df.format(i);
			if (getInstance().containsKey(key)) {
				list.addElement(getStringProperty(key));
				i++;
			} else {
				break;
			}
		}		
		
		return list;
	}
	
	public static boolean getBooleanProperty(String key) {
		Boolean	tb = new Boolean((String) getInstance().get(key));
		return tb.booleanValue();
	}
	
	//Weitere get Methoden...
	
	public static void putStringProperty(String key, String value) {
		getInstance().put(key,value);
	}
	
	public static void putDoubleProperty(String key, double value) {
		getInstance().put(key,String.valueOf(value));
	}
	
	public static void putIntProperty(String key, int value) {
		getInstance().put(key,String.valueOf(value));
	}

	public static void putBooleanProperty(String key, boolean value) {
		getInstance().put(key, String.valueOf(value));
	}
	
	public static void store() {
						
		if (fileName == null)
			fileName = defaultFileName;
		
		try {
			FileOutputStream out = new FileOutputStream(fileName);
      	getInstance().store(out, null);
      	out.close();
		} catch (IOException ioe) {
			System.err.println("AppProperties11.saveProperties : " + ioe);
		}

	}
	
	public static AppProperties11 getInstance() {
		if (instance == null) {			
			synchronized(AppProperties11.class) {
				if (instance == null) 
					instance = new AppProperties11();
			}
		}
		return instance;					
	}	
}