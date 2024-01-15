package gtf.crapa;

import java.util.*;
import java.io.*;

public class CrapaCodeDescription {

    private Map descriptions;
    private static CrapaCodeDescription instance = new CrapaCodeDescription();
    
    private CrapaCodeDescription() {
    	descriptions = new TreeMap();
    	
    	String line;
		InputStream is = getClass().getResourceAsStream("crapacodes.dat");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			while ((line = buf.readLine()) != null) {
				try {
					descriptions.put(new Integer(line.substring(0,3)),line.substring(4).trim());            
				} catch (NumberFormatException nfe) {
					System.err.println(nfe);			
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}

    }

    public static String getDescription(int cc) {
        return (String)instance.descriptions.get(new Integer(cc));
    }        

}