
import java.util.*;
import COM.odi.util.*;

public class Branches {
    private Map branches;
    
    public Branches() {
        
        branches = new OSHashMap();
        
        StringTokenizer st = new StringTokenizer(AppProperties.getProperty("branches"), ",");
        while (st.hasMoreTokens()) {
            String br = st.nextToken();
            Branch branch = new Branch(br);
            
            branches.put(branch.getName(), branch);                        
        }        
        
        //All Branch 
        AllBranch ab = new AllBranch();
        branches.put(ab.getName(), ab);
    }

    public Iterator valuesIterator() {        
        return branches.values().iterator();
    }

    public Branch getBranch(String branchStr) {
        return (Branch)branches.get(branchStr);
    }        
    
}

