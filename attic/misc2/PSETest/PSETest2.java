
import COM.odi.*;
import com.objectspace.djgl.*;
import java.util.*;
import java.io.*;

public class PSETest2
{
    static final String DB_NAME = "Liability.odb";
    static final String CONT_LIABILITY_ROOT = "ContLiability";
    static final String FIRM_LIABILITY_ROOT = "FirmLiability";
    
    OrderedMap firmMap, contMap;
    
    void go() {
        Database db;
        Session session = Session.create(null, null);        
        session.join();

        try {
            db = Database.open(DB_NAME, ObjectStore.UPDATE);          
            Transaction tr = Transaction.begin(ObjectStore.READONLY);           
            contMap = (OrderedMap)db.getRoot(CONT_LIABILITY_ROOT);
            
            Integer key = Liability.createKey(999, "0835");
            Enumeration values = contMap.values(key);
            while ( values.hasMoreElements() )
              System.out.println(values.nextElement());
            
            tr.commit();
            session.terminate();
            
        } catch(DatabaseNotFoundException e) { 
            System.out.println(e);            
        }
        
    }
    

    
    public static void main(String args[])
    {
            new PSETest2().go();        
    }
    
    
}
