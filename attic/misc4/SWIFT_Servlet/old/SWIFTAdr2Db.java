import java.io.*;
import java.util.*;
import COM.odi.*;
import COM.odi.util.*;
import java.text.*;

public class SWIFTAdr2Db {
    

    public SWIFTAdr2Db(String args[]) {

        Session session = DbManager.createSession();
        session.join();                
        DbManager.initialize(false);
        
        Database db = Database.open(DbManager.DB_NAME, ObjectStore.UPDATE);        
 
        Transaction tr = Transaction.begin(ObjectStore.UPDATE);   
        Map addressMap = (OSTreeMapString)db.getRoot(DbManager.SWIFT_ADR_ROOT);
        
        readFileIntoDb(args[0], addressMap);
        
        tr.commit();

        db.close();
        db.GC();
        session.terminate();
    }
            
    private void readFileIntoDb(String fileName, Map aMap) {
        BufferedReader dis;
        String line;
      	
        try {                
            dis = new BufferedReader(new FileReader(fileName));     
            while ((line = dis.readLine()) != null) {
                if (line.length() > 48) {
                    String adr = line.substring(0,11);
                    String value = line.substring(12, 48).trim() + ", " + line.substring(48).trim();
                                   
                    aMap.put(adr, value);               
	            }
	        }
            dis.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
        catch (IOException e) {
             System.err.println(e);
        }
    }

    public static void main(String args[]) {
        if (args.length == 1)
            new SWIFTAdr2Db(args);
        else
            System.out.println("java SWIFTAdr2Db <fileName>");
    }

}
