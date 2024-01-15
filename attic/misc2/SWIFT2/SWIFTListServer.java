import java.util.*;
import java.text.*;
import com.objectspace.voyager.*;
import COM.odi.*;
import COM.odi.util.*;

public class SWIFTListServer implements ISWIFTListServer {


    public SWIFTListServer() {
        DbManager.initialize(false);
    }


    public Calendar getFromDate() {
        DbManager.startReadTransaction();
        long millis = DbManager.getFromDate();
        DbManager.commitTransaction();
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date(millis));
        return cal;        
    }
    
    public Calendar getToDate() {
        DbManager.startReadTransaction();
        long millis = DbManager.getToDate();
        DbManager.commitTransaction();
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date(millis));
        return cal;                
    }

    public int getNumberOfHeaders() {
        DbManager.startReadTransaction();
        int size = DbManager.getSWIFTInputCollection().size();
        DbManager.commitTransaction();
        return size;
    }

    public SWIFTHeader getSWIFTHeader(String tosn) {        
        if (tosn != null) {
            DbManager.startUpdateTransaction();       
            SWIFTHeader sh = (SWIFTHeader)(DbManager.getSWIFTInputCollection().get(tosn));
            ObjectStore.deepFetch(sh);
            DbManager.commitReadOnlyTransaction();
            return sh;
        }
        else
            return null;
    }


    public Collection getSWIFTHeaders(Calendar fromDate, Calendar toDate) {
        OSVectorList vect = new OSVectorList();
        SWIFTHeader sh;
 		if (fromDate != null && toDate != null) {
 		    DbManager.startUpdateTransaction();
 		    fromDate.add(Calendar.DATE, -1);
 		    toDate.add(Calendar.DATE, +1);
 		    
 		    Iterator it = DbManager.getSWIFTInputCollection().values().iterator();
 		    while(it.hasNext()) {
 		        sh = (SWIFTHeader)(it.next());
 		        if (sh.getReceive_Date().after(fromDate) && sh.getReceive_Date().before(toDate)) 		            
 		            vect.add(sh);
 		    }
 		    DbManager.commitReadOnlyTransaction();
            return vect;
 		    
        } else
            return null;                
    }


    public static void main(String args[]) {
        try {
            Voyager.startup(7500);
            ISWIFTListServer isls = (ISWIFTListServer)Voyager.construct("SWIFTListServer", "localhost:7500");
            Identity.of(isls).setAlias("SWIFTListServer");
          	Lifecycle.of(isls).liveForever();
        }
        catch(Exception exception ) {
            System.err.println(exception);
        }
    }
}
