import java.util.*;
import common.util.AppProperties;

public class CrapaCodeDescription {

    private Map descriptions;
    private static CrapaCodeDescription instance = new CrapaCodeDescription();
    
    private CrapaCodeDescription() {
    	descriptions = new TreeMap();
    	
		List codeList = AppProperties.getStringArrayProperty("code");
		Iterator it = codeList.iterator();
		while(it.hasNext()) {
			try {
				String codeStr = (String)it.next();
				descriptions.put(new Integer(codeStr.substring(0,3)),codeStr.substring(4).trim());            
			} catch (NumberFormatException nfe) {
				System.err.println(nfe);			
			}
		}

    }

    public static String getDescription(int cc) {
        return (String)instance.descriptions.get(new Integer(cc));
    }        

}
