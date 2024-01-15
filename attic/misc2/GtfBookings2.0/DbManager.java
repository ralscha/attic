import java.util.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;
import COM.odi.util.query.*;

public class DbManager {
    private static Database db;
    private static Session session;

    static final String CONT_LIABILITY_ROOT = "ContLiability";
    static final String FIRM_LIABILITY_ROOT = "FirmLiability";
    
    private static OSTreeSet firm, cont;
    private static Transaction updateTransaction;

    public static Collection getFirmCollection() {
        return firm;
    }
    
    public static Collection getContCollection() {
        return cont;
    }
    
    public static int getFirmCollectionSize() {
        Transaction tr = Transaction.begin(ObjectStore.READONLY);
        int size = firm.size();
        tr.commit(ObjectStore.RETAIN_HOLLOW);        
        return size;

    }
    
    public static int getContCollectionSize() {
        Transaction tr = Transaction.begin(ObjectStore.READONLY);        
        int size = cont.size();
        tr.commit(ObjectStore.RETAIN_HOLLOW);                
        return size;
    }
    
    public static void initialize(String dbName, boolean createNew) {
        session = Session.createGlobal(null, null);        

        if (createNew) {
            try {
                Database.open(dbName, ObjectStore.UPDATE).destroy();
            } catch(DatabaseNotFoundException e) { }
        }

        try {
            db = Database.open(dbName, ObjectStore.OPEN_UPDATE);
        } catch (DatabaseNotFoundException e) {
            db = Database.create(dbName, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
        }

        Transaction tr = Transaction.begin(ObjectStore.UPDATE);
        try {
            firm = (OSTreeSet)db.getRoot(FIRM_LIABILITY_ROOT);
            cont = (OSTreeSet)db.getRoot(CONT_LIABILITY_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(FIRM_LIABILITY_ROOT, firm = new OSTreeSet(db));
            db.createRoot(CONT_LIABILITY_ROOT, cont = new OSTreeSet(db));
        }        
        
        tr.commit(ObjectStore.RETAIN_HOLLOW);        
    }

    public static void removeIndex() {
        firm.dropIndex(Liability.class, "getGtf_number()");
        firm.dropIndex(Liability.class, "getGtf_proc_center()");
        cont.dropIndex(Liability.class, "getGtf_number()");
        cont.dropIndex(Liability.class, "getGtf_proc_center()");       
    }

    public static void createIndex() {
        try {
            firm.addIndex(Liability.class, "getGtf_number()");
            firm.addIndex(Liability.class, "getGtf_proc_center()");
            cont.addIndex(Liability.class, "getGtf_number()");
            cont.addIndex(Liability.class, "getGtf_proc_center()");       
        } catch (IllegalAccessException i) {
            shutdown();
            System.exit(1);
        }
    }

    public static void shutdown() 
    {
        db.close();
        session.terminate();
        return;
    }

    public static void startUpdateTransaction() {
        updateTransaction = Transaction.begin(ObjectStore.UPDATE);        
    }
    
    public static void commitUpdateTransaction() {
        updateTransaction.commit(ObjectStore.RETAIN_HOLLOW);
    }

    public static void addLiability(Liability liab) {
        if (liab != null) {           
            if (liab instanceof ContingentLiability)
                cont.add(liab);
            else if (liab instanceof FirmLiability)
                firm.add(liab);
        }
    }

}
