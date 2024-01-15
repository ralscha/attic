import COM.odi.util.*;
import COM.odi.*;

public class SWIFTAdrSearch {
    
    private Session session;
           
    public SWIFTAdrSearch(String swiftAdr) {
        
        try {
            session = DbManager.createSession();
            session.join();

            if (swiftAdr.equalsIgnoreCase("INDEX")) {
                createNGramList();
                System.out.println("Index created");                
                return;
            }
            Transaction tr = null;
        
            Database db = Database.open(DbManager.DB_NAME, ObjectStore.READONLY);           
            tr = Transaction.begin(ObjectStore.READONLY);     
            Map swiftAdrMap = (OSTreeMapString)db.getRoot(DbManager.SWIFT_ADR_ROOT);                        
            Map nGramMap = (OSTreeMapString)db.getRoot("NGRAM");
            
            if (swiftAdr.length() < 6) {
                Set sa = (Set)nGramMap.get(swiftAdr.substring(0,3));
                Iterator it = sa.iterator();
                while(it.hasNext())
                    System.out.println((String)swiftAdrMap.get((String)it.next()));
            } else {
                Set sa1 = (Set)nGramMap.get(swiftAdr.substring(0,3));
                Set sa2 = (Set)nGramMap.get(swiftAdr.substring(3,6));
                if ((sa1 != null) && (sa2 != null)) {
                    Iterator it = sa1.iterator();
                    while(it.hasNext()) {
                        String a = (String)it.next();
                        if (sa2.contains(a))
                            System.out.println(a);
                    }
                    
                }
                else if (sa1 != null) {
                    Iterator it = sa1.iterator();
                    while(it.hasNext())
                        System.out.println((String)swiftAdrMap.get((String)it.next()));
                    
                }
                else if (sa2 != null) {
                    Iterator it = sa2.iterator();
                    while(it.hasNext())
                        System.out.println((String)swiftAdrMap.get((String)it.next()));                    
                }
                
            }
            tr.commit();
        } finally {
            System.out.println("FINALLY");
            session.terminate();
        }

        
    }
    
    private void createNGramList() {
        Database db = Database.open(DbManager.DB_NAME, ObjectStore.UPDATE);        
        Transaction tr = Transaction.begin(ObjectStore.UPDATE);   
        Map swiftAdrMap = (OSTreeMapString)db.getRoot(DbManager.SWIFT_ADR_ROOT);                        
        
        Map nGramMap = null;
        
        int cp_counter = 0;
        
        try {        
            nGramMap = (OSTreeMapString)db.getRoot("NGRAM");
            nGramMap.clear();
        } catch (DatabaseRootNotFoundException e) {
            db.createRoot("NGRAM", nGramMap = new OSTreeMapString(db));
        }                        
        
        Set keys = swiftAdrMap.keySet();
        Iterator it = keys.iterator();
        Set adrmap;
        
        while (it.hasNext()) {
            cp_counter++;
            
            String sa = (String)it.next();
                        
            if ((adrmap = (Set)nGramMap.get(sa.substring(0,3))) == null) {
                adrmap = new OSHashSet();
                nGramMap.put(sa.substring(0,3), adrmap);
            }
            adrmap.add(sa);
            

            if ((adrmap = (Set)nGramMap.get(sa.substring(3,6))) == null) {
                adrmap = new OSHashSet();
                nGramMap.put(sa.substring(3,6), adrmap);
            }
            adrmap.add(sa);


            if ((adrmap = (Set)nGramMap.get(sa.substring(6,9))) == null) {
                adrmap = new OSHashSet();                
                nGramMap.put(sa.substring(6,9), adrmap);
            }
            adrmap.add(sa);
            
            if (cp_counter >= 500) {
                tr.checkpoint(ObjectStore.RETAIN_HOLLOW);
                cp_counter = 0;
            }
        }
                       
        tr.commit();
        db.close();
    }
    
    public static void main(String args[]) {
        if (args.length == 1)
            if (args[0].length() >= 3)
                new SWIFTAdrSearch(args[0]);  
            else
                System.out.println("Mehr als 2 Zeichen eingeben");
        else 
            System.out.println("java SWIFTAdrSearch <SWIFTADR>");
    }

}
