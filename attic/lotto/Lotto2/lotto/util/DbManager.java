package lotto.util;

import lotto.*;

import java.util.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;

public class DbManager {
    private static Database db;
    private static Session session;

    static final String ROOT_STR = "Ausspielungen";
    
    private static Ausspielungen ausspielungen;
    private static Transaction transaction;

    public static Ausspielungen getAusspielungen() {        
        return ausspielungen;
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
            ausspielungen = (Ausspielungen)db.getRoot(ROOT_STR);
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot(ROOT_STR, ausspielungen = new Ausspielungen());
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

}
