package db4o;

import java.util.HashMap;
import java.util.Map;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class IdGenerator {
  
  static {
    Db4o.licensedTo("ralphschaer@yahoo.com");
  }
  
  public final static String BEDARF = "bedarf";
  public final static String ANGEBOT = "angebot";
  
  private static IdGenerator instance = new IdGenerator();
  
  private Map idMap;
  private ObjectContainer db; 
  
  private IdGenerator() {
    //idMap = new HashMap();
    db = Db4o.openFile("ids.yap");
    
    ObjectSet set = db.get(new HashMap());
    
    if (set.hasNext()) {
      //System.out.println(set.next().getClass());
      idMap = (HashMap)set.next();
    } else {
      idMap = new HashMap();
      store();
    }
  } 

  public static int getNextId(String object) {
    try {
      Integer lastId = (Integer)instance.idMap.get(object);
      if (lastId != null) {
        int id = lastId.intValue();
        id++;
        
        instance.idMap.put(object, new Integer(id));
        return id;
      } else {
      
        instance.idMap.put(object, new Integer(0));
        return 0;
      }
    } finally {
      instance.store();
    }    
  }
  
  private void store() {
    db.set(idMap);
  }
  
  public static void close() {
    instance.db.close();
  }
  
  public static void main(String[] args) {    
    System.out.println(IdGenerator.getNextId(ANGEBOT));
    System.out.println(IdGenerator.getNextId(BEDARF));
    IdGenerator.close();    
    
  }
}
