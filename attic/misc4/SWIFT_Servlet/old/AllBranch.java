
import java.util.*;
import COM.odi.util.*;

public class AllBranch extends Branch {

    public AllBranch() {
        super(null);
    }

    public boolean contains(String b) {                
        return true;
    }
    
    public String getHTMLName() {
        return "ALL";    
    }
    
    public String getName() {
        return "ALL";
    }
}
