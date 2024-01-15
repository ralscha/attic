import java.util.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;

public class DbManager {
    private static Database db;
    private static Session session;

    static final String DB_NAME = "SWIFTInput.odb";
    static final String ROOT_STR = "SWIFTInput";
    static final String FROM_DATE_ROOT_STR = "SWIFTInputFromDate";
    static final String TO_DATE_ROOT_STR   = "SWIFTInputToDate";
    
    private static OSHashMap swiftInputCollection ;
    private static Long fromDate, toDate;
    
    private static Transaction transaction;

    public static OSHashMap getSWIFTInputCollection() {
        return swiftInputCollection;
    }
    
    public static long getFromDate() {
        return fromDate.longValue();
    }

    public static long getToDate() {
        return toDate.longValue();
    }
    
    public static void initialize(boolean createNew) {
        session = Session.createGlobal(null, null);        

        if (createNew) {
            try {
                Database.open(DB_NAME, ObjectStore.UPDATE).destroy();
            } catch(DatabaseNotFoundException e) { }
        }

        try {
            db = Database.open(DB_NAME, ObjectStore.OPEN_UPDATE);
        } catch (DatabaseNotFoundException e) {
            db = Database.create(DB_NAME, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
        }

        Transaction tr = Transaction.begin(ObjectStore.UPDATE);
        try {
            swiftInputCollection = (OSHashMap)db.getRoot(ROOT_STR);
            fromDate = (Long)db.getRoot(FROM_DATE_ROOT_STR);
            toDate   = (Long)db.getRoot(TO_DATE_ROOT_STR);            
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(ROOT_STR, swiftInputCollection = new OSHashMap());
            db.createRoot(FROM_DATE_ROOT_STR, fromDate = new Long(0));
            db.createRoot(TO_DATE_ROOT_STR, toDate = new Long(0));
        }        
        
        tr.commit(ObjectStore.RETAIN_HOLLOW);        
    }


    public static void shutdown() {
        db.close();
        session.terminate();
        return;
    }

    public static void startUpdateTransaction() {
        transaction = Transaction.begin(ObjectStore.UPDATE);        
    }

    public static void startReadTransaction() {
        transaction = Transaction.begin(ObjectStore.READONLY);        
    }
    
    public static void commitTransaction() {
        transaction.commit(ObjectStore.RETAIN_HOLLOW);
    }
    
    public static void commitReadOnlyTransaction() {
        transaction.commit(ObjectStore.RETAIN_READONLY);
    }
    
    public static void setToDate(long millis) {
        toDate = new Long(millis);
        db.setRoot(TO_DATE_ROOT_STR, toDate);
    }
    
    public static void setFromDate(long millis) {
        fromDate = new Long(millis);
        db.setRoot(FROM_DATE_ROOT_STR, fromDate);
    }
    
    public static void addSWIFT(SWIFTHeader sh) {
        if (sh != null)
            swiftInputCollection.put(sh.getKey(), sh);
    }

}
