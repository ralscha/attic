import java.util.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;
import java.text.*;

public class DbManager {

    public static String DB_NAME = "";
    public static String BOOKING_DB_NAME = "";
    public static final String ROOT_STR = "SWIFTInput";
    public static final String DATE_COLL_ROOT_STR = "SWIFTInputDates";
    public static final String LAST_UPDATE_ROOT_STR   = "lastUpdate";
    public static final String SWIFT_ADR_ROOT = "SWIFTAdr";
    public static final String STATE_ROOT = "State";
    public static final String MISSING_SWIFT_ROOT = "Missing";
    public static final String BOOKING_ROOT = "Booking";
    public static final String SPECIAL_BOOKING_ROOT = "SpecialBooking";
    
    private static boolean isInit = false;
    private static boolean bookingdb_isInit = false;
    
    static {
        String h = AppProperties.getProperty("database");
        if (h == null)
            h = "SWIFTInput.odb";
        DB_NAME = h;    
        
        h = AppProperties.getProperty("booking_database");
        if (h == null)
            h = "Bookings.odb";
        BOOKING_DB_NAME = h;    
    }
           
           
    public static boolean isInitialized() {
        return isInit;
    }

    public static boolean isBookingDBInitialized() {
        return bookingdb_isInit;
    }

    
    public static Session createSession() {
        Properties prop = new Properties();
        prop.put("COM.odi.useDatabaseLocking", "false");                
        return Session.create(null, prop);
    }
    
    public static void initialize_bookingdb(boolean createNew) {
        Database db;
        if (createNew) {
            try {
                Database.open(BOOKING_DB_NAME, ObjectStore.UPDATE).destroy();
            } catch(DatabaseNotFoundException e) { }            
        }
        try {
            db = Database.open(BOOKING_DB_NAME, ObjectStore.OPEN_UPDATE);
        } catch (DatabaseNotFoundException e) {
            db = Database.create(BOOKING_DB_NAME, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
        }
        
        Transaction tr = Transaction.begin(ObjectStore.UPDATE);
        try {
            List tm = (OSVectorList)db.getRoot(BOOKING_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(BOOKING_ROOT, new OSVectorList());
        }

        try {
            List li = (OSVectorList)db.getRoot(SPECIAL_BOOKING_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(SPECIAL_BOOKING_ROOT, new OSVectorList());
        }

        
        try {
            Long u = (Long)db.getRoot(LAST_UPDATE_ROOT_STR);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(LAST_UPDATE_ROOT_STR, new Long(0));
        }
        
        tr.commit();
        db.close();        
        bookingdb_isInit = true;
    }
    
    public static void initialize(boolean createNew) {        
        Database db;
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
            Map tm = (OSTreeMapString)db.getRoot(ROOT_STR);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(ROOT_STR, new OSTreeMapString(db));
        }
        
        try {
            Map si = (OSHashMap)db.getRoot(DATE_COLL_ROOT_STR);
        } catch (DatabaseRootNotFoundException e) {            
            db.createRoot(DATE_COLL_ROOT_STR, new OSHashMap());
        }

        try {
            Long u = (Long)db.getRoot(LAST_UPDATE_ROOT_STR);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(LAST_UPDATE_ROOT_STR, new Long(0));
        }
  
        try {
            Set se = (OSHashSet)db.getRoot(STATE_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(STATE_ROOT, new OSHashSet());
        }
      
        
        try {        
            Map sa = (OSTreeMapString)db.getRoot(SWIFT_ADR_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(SWIFT_ADR_ROOT, new OSTreeMapString(db));
        }                
        
        try {
            List li = (OSVectorList)db.getRoot(MISSING_SWIFT_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(MISSING_SWIFT_ROOT, new OSVectorList());
        }


        
        tr.commit();        
        db.close();
        isInit = true;
    }

}
