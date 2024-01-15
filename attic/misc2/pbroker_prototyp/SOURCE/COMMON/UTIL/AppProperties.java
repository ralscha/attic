package common.util;

import java.util.*;
import java.io.*;
import java.text.*;

public final class AppProperties extends Properties {

	private static DecimalFormat df = new DecimalFormat("000");
	private static AppProperties instance;
	private static String fileName = null;
	private static String defaultFileName = "app.props";


	protected AppProperties() {
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
			} else {
				//Probieren wir's mal mit getResourceAsStream
				
				InputStream is = getClass().getResourceAsStream(defaultFileName);
				
				if (is == null) {
					is = getClass().getResourceAsStream("/" + defaultFileName);
					//is = ClassLoader.getSystemResourceAsStream(defaultFileName);
				}
				
				if (is != null) {
					load(is);
					is.close();					
				} 
				
			}

		} catch (IOException ioe) {
			System.err.println("Constructor AppProperties : " + ioe);
		}
	}

	public static void setFileName(String datFile) {
		fileName = datFile;
	}

	public static Double getDoubleProperty(String key) throws NumberFormatException {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return new Double(value);
		else
			return null;
	}

	public static double getDoubleProperty(String key, double defaultValue) throws NumberFormatException {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return Double.parseDouble(value);
		else
			return defaultValue;
	}
	
	
	public static Integer getIntegerProperty(String key) throws NumberFormatException {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return new Integer(value);
		else
			return null;
	}

	public static Long getLongProperty(String key) throws NumberFormatException {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return new Long(value);
		else
			return null;
	}
	
	
	public static int getIntegerProperty(String key, int defaultValue) throws NumberFormatException {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return Integer.parseInt(value);
		else
			return defaultValue;
	}
	
	
	
	public static String getStringProperty(String key) {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return (value.trim());
		else
			return null;
	}

	public static String getStringProperty(String key, String defaultValue) {
		String value = getInstance().getProperty(key, defaultValue);
		if (value != null)
			return (value.trim());
		else
			return null;
	}
	
	public static Boolean getBooleanProperty(String key) {
		String value = getInstance().getProperty(key);
		if (value != null) 
			return (new Boolean(value.trim()));
		else
			return null;
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {		
		String value = getInstance().getProperty(key);
		if (value != null) 
			return (new Boolean(value.trim()).booleanValue());
		else
			return defaultValue;
	}
	
	
	public static List getStringArrayProperty(String keyStr) {
		List list = new ArrayList();

		int i = 1;

		while (true) {
			String key = keyStr + '.' + df.format(i);

			if (getInstance().containsKey(key)) {
				list.add(getStringProperty(key));
				i++;
			} else {
				break;
			}
		}

		return list;
	}

	public static void putStringProperty(String key, String value) {
		getInstance().put(key, value);
	}

	public static void putDoubleProperty(String key, double value) {
		getInstance().put(key, String.valueOf(value));
	}

	public static void putIntProperty(String key, int value) {
		getInstance().put(key, String.valueOf(value));
	}
	
	public static void putLongProperty(String key, long value) {
		getInstance().put(key, String.valueOf(value));
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
			System.err.println("AppProperties.saveProperties : " + ioe);
		}

	}


	public static AppProperties getInstance() {
		if (instance == null) {
			synchronized (AppProperties.class) {
				if (instance == null)
					instance = new AppProperties();
			}
		}
		return instance;
	}
	
	
	//Utiliy method
	
	public static void setProxyProperties() {
		String proxyHost = getStringProperty("proxy.host");
		String proxyPort = getStringProperty("proxy.port");
		
		if ((proxyHost != null) && (proxyHost.trim().length() > 0)) {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", proxyHost);
			System.getProperties().put("proxyPort", proxyPort); 
		}
	}


}