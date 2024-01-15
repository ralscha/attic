import java.io.*;
import java.util.*;
import common.util.*;

public class Branches {

	private static Branches instance = new Branches();
	private Map branchesMap;

    private Branches() {
    	super();
    	
		String line;
		String fileName = AppProperties.getStringProperty("branches.file");
		
		
		try {
			BufferedReader buf = new BufferedReader(new FileReader(fileName));     
			while ((line = buf.readLine()) != null) {                
				try {
					String ibbb = line.substring(0,4);
					Integer rz = new Integer(line.substring(4));
					branchesMap.put(ibbb, rz);
				} catch (NumberFormatException nfe) { }
			}
		} catch (FileNotFoundException fnfe) { 
			System.err.println(fnfe);                                           
		} catch (IOException ioe) {
			System.err.println(ioe);
		}

    }

    public static int getRZ(String ibbb) {
        return ((Integer)instance.branchesMap.get(ibbb)).intValue();
    }        

}

