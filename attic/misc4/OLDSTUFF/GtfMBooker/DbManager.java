
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;
import COM.odi.util.query.*;

public class DbManager {
    private static Database db;
    private static Session session;

    static final String CONT_LIABILITY_ROOT = "ContLiability";
    static final String FIRM_LIABILITY_ROOT = "FirmLiability";
    
    private static OSVectorList firm, cont;

    public static Collection getFirmCollection() {
        return firm;
    }
    
    public static Collection getContCollection() {
        return cont;
    }
    
    public static int getFirmCollectionSize() {
        return firm.size();
    }
    
    public static int getContCollectionSize() {    
        return cont.size();
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
            firm = (OSVectorList)db.getRoot(FIRM_LIABILITY_ROOT);
            cont = (OSVectorList)db.getRoot(CONT_LIABILITY_ROOT);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(FIRM_LIABILITY_ROOT, firm = new OSVectorList());
            db.createRoot(CONT_LIABILITY_ROOT, cont = new OSVectorList());
        }        
        
        tr.commit(ObjectStore.RETAIN_HOLLOW);        
    }

   
    public static void shutdown() 
    {
        db.close();
        session.terminate();
        return;
    }

	public static Transaction startReadTransaction() {
		return(Transaction.begin(ObjectStore.READONLY));
	}
	 
	public static void commitTransaction(Transaction tr) {
		tr.commit(ObjectStore.RETAIN_HOLLOW);
	}

    public static Transaction startUpdateTransaction() {
		return(Transaction.begin(ObjectStore.UPDATE));        
    }
    
    public static void commitUpdateTransaction(Transaction tr) {
		tr.commit(ObjectStore.RETAIN_HOLLOW);
    }

    public static void addLiability(Liability liab) {
        if (liab != null) {           
            if (liab instanceof ContingentLiability) {
            	System.out.println("CONT");
                cont.add(liab);
            }
            else if (liab instanceof FirmLiability) {
            	System.out.println("FIRM");
                firm.add(liab);
            }
        }
    }

}