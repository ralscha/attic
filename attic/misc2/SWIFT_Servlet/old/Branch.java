
import java.util.*;
import COM.odi.util.*;

public class Branch {
    private Set in;
    private String name;
    private String htmlName;

    public Branch(String propName) {
        if (propName == null) return;
        
        in = new OSHashSet();
        StringTokenizer st = new StringTokenizer(AppProperties.getProperty(propName), ",");
        name = st.nextToken();
        htmlName = st.nextToken();
        
        while (st.hasMoreTokens()) {
            in.add(st.nextToken());
        }
    }
   
    public boolean contains(String b) {                
        return in.contains(b);
    }
    
    public String getHTMLName() {
        return htmlName;    
    }
    
    public String getName() {
        return name;
    }
}
