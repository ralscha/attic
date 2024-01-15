import java.util.*;
import common.util.AppProperties;

public class Branches {

	private Map branches;
	private static Branches instance = new Branches();

	private Branches() {
		branches = new TreeMap();
		
		List branchList = AppProperties.getStringArrayProperty("branch");
		Iterator it = branchList.iterator();
		while(it.hasNext()) {
			String brStr = (String)it.next();
			branches.put(brStr.substring(0,4),brStr.substring(5).trim());            
		}
	}

    public static String getName(String ibbb) {
        return (String)instance.branches.get(ibbb);
    }        

}