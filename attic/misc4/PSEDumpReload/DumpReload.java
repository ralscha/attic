import COM.odi.*;
import COM.odi.util.*;
import java.io.*;
import java.util.*;


public class DumpReload {

    public static final String ROOT_STR = "SWIFTInput";
    public static final String DATE_COLL_ROOT_STR = "SWIFTInputDates";
    public static final String LAST_UPDATE_ROOT_STR   = "lastUpdate";
    public static final String SWIFT_ADR_ROOT = "SWIFTAdr";
    public static final String STATE_ROOT = "State";
    
    static public void main(String argv[]) throws Exception {
    
        if (argv.length >= 2) {
            if (argv[0].equalsIgnoreCase("dump")) {
                dumpDatabase(argv[1], argv[2]);
            } else if (argv[0].equalsIgnoreCase("reload")) {
                reloadDatabase(argv[1], argv[2]);
            } else {
                usage();
            }
            
        } else {
            usage();
        }
    }
      
    static void usage() {
        System.err.println(
                  "Usage: java DumpReload OPERATION ARGS...\n" +
                  "Operations:\n" +
                  "   dump FROMDB TOFILE\n" +
                  "   reload TODB FROMFILE\n");
        System.exit(1);
    }
    
    static void dumpDatabase(String dbName, String dumpName) throws Exception {
        
        Session session = Session.create(null, null);
        session.join();                

        Database db = Database.open(dbName, ObjectStore.READONLY);
        FileOutputStream fos = new FileOutputStream(dumpName);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        Transaction t = Transaction.begin(ObjectStore.READONLY);

        Map tm = (OSTreeMapString)db.getRoot(ROOT_STR);
        out.writeObject(new Integer(tm.size()));
        writeMapObjects(out,tm,true);


        Map si = (OSHashMap)db.getRoot(DATE_COLL_ROOT_STR);
        out.writeObject(new Integer(si.size()));
        writeMapObjects(out,si,true);

        Set se = (OSHashSet)db.getRoot(STATE_ROOT);
        out.writeObject(new Integer(se.size()));
        writeObjects(out,se.iterator());


        Map sa = (OSTreeMapString)db.getRoot(SWIFT_ADR_ROOT);
        out.writeObject(new Integer(sa.size()));
        writeMapObjects(out,sa,false);

        Long u = (Long)db.getRoot(LAST_UPDATE_ROOT_STR);
        out.writeObject(u);

               
        t.commit();
        out.close();
        db.close();
        session.terminate();
    }

    static void writeObjects(ObjectOutputStream out, Iterator it) throws Exception {    
        while(it.hasNext()) {
            Object obj = it.next();
            ObjectStore.deepFetch(obj);
            out.writeObject(obj);
        }
    }
      
    static void writeMapObjects(ObjectOutputStream out, Map map, boolean deep) throws Exception {    
        Iterator it = map.keySet().iterator();
        while(it.hasNext()) {
            
            Object obj = it.next();
            out.writeObject(obj);
            
            Object objEntry = map.get(obj);
            if (deep)
                ObjectStore.deepFetch(objEntry);
            out.writeObject(objEntry);
        }
    }


    static void reloadDatabase(String dbName, String dumpName) throws Exception {
                
        Session session = Session.create(null, null);
        session.join();                

        try {
            Database.open(dbName, ObjectStore.OPEN_UPDATE).destroy();
        } catch (DatabaseNotFoundException DNFE) { }
        
        Database db = Database.create(dbName, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
        
        
        FileInputStream fis = new FileInputStream(dumpName);
        ObjectInputStream in = new ObjectInputStream(fis);
        Transaction tr = Transaction.begin(ObjectStore.UPDATE);
                

        int size;
        
        size = ((Integer)in.readObject()).intValue();        
        Map tm;
        db.createRoot(ROOT_STR, tm = new OSTreeMapString(db));        
        readMapObjects(in, tm, size);
        
        size = ((Integer)in.readObject()).intValue();        
        Map si;
        db.createRoot(DATE_COLL_ROOT_STR, si = new OSHashMap());        
        readMapObjects(in, si, size);        
        
        size = ((Integer)in.readObject()).intValue();        
        Set se;
        db.createRoot(STATE_ROOT, se = new OSHashSet());
        readObjects(in, se, size);        
        
        size = ((Integer)in.readObject()).intValue();        
        Map sa;
        db.createRoot(SWIFT_ADR_ROOT, sa = new OSTreeMapString(db));
        readMapObjects(in, sa, size);    
        
        Long u = (Long)in.readObject();        
        db.createRoot(LAST_UPDATE_ROOT_STR, u);
                
        tr.commit();
        db.close();
        db.GC();
        session.terminate();
    }
    
    static void readObjects(ObjectInputStream in, Collection inColl, int size) throws Exception {    
        for (int i = 0; i < size; i++)
            inColl.add(in.readObject());

    }
      
    static void readMapObjects(ObjectInputStream in, Map inMap, int size) throws Exception {    
        for (int i = 0; i < size; i++) {
            Object key = in.readObject();
            Object value = in.readObject();
            inMap.put(key, value);
        }        
    }
    
    
}