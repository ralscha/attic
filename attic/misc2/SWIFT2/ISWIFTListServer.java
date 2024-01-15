import java.util.*;
import COM.odi.util.*;

public interface ISWIFTListServer {
    public SWIFTHeader getSWIFTHeader(String tosn) ;
    public Collection getSWIFTHeaders(Calendar fromDate, Calendar toDate) ;
    public int getNumberOfHeaders() ;
    
    public Calendar getFromDate() ;
    public Calendar getToDate() ;
    
}
