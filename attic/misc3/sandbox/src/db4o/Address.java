package db4o;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Address {

   public String street;
   private String postalCode;
   private String city;
   private Map testMap;
   private int id;
  
  public static void main(String[] args) {
    Db4o.licensedTo("ralphschaer@yahoo.com");
    ObjectContainer db = Db4o.openFile("myDataFile.yap");
    
  /*
    for (int i = 0; i < 10; i++) {
      Address newAddress = new Address();
      newAddress.setCity("siselen"+i);
      newAddress.setPostalCode(String.valueOf(i));
      newAddress.setStreet("juchen "+i);
      newAddress.setId(i);
      
      Map m = new HashMap();
      m.put(new Integer(i), "haba"+i);
      m.put(new Integer(100+i), "aa"+i);
      m.put(new Integer(200+i), "xx"+i);
      newAddress.setTestMap(m);
      db.set(newAddress);
      
    }
    */

/*
    Address example = new Address();
    Map m = new HashMap();
    m.put(new Integer(0), "haba0");
    example.setTestMap(m);
    ObjectSet set = db.get(example);
    
    while (set.hasNext()) {      
      Address result = (Address)set.next();
      System.out.println(result);
    }
  */  

    Query query = db.query();
    query.constrain(Address.class);
    
    
    //Query qi = query.descend("testMap");    
    //qi.constrain();
    
    
    ObjectSet set = query.execute();
    while (set.hasNext()) {      
      Address result = (Address)set.next();
      System.out.println(result);
    }
    

    
    /*
    Address newAddress = new Address();
    newAddress.setCity("siselen");
    newAddress.setPostalCode("2577");
    newAddress.setStreet("juchen 8");
    
    db.set(newAddress);
    */

    //Address example = new Address();
    //example.setPostalCode("2578");
    
    
    //ObjectSet set = db.get(example);
    //Address result = (Address)set.next();
    
    /*
    Map m = new HashMap();
    m.put("one", "ich");
    result.setTestMap(m);
    
    db.set(result);
    */
    
    //System.out.println(result);
    
    /*
    Query query = db.query();
    query.constrain(Address.class);
    
    
    Query qi = query.descend("street");    
    qi.constrain("juc").like();
    qi.orderAscending();
    
    ObjectSet set = query.execute();
    while (set.hasNext()) {      
      Address result = (Address)set.next();
      System.out.println(result);
    }
    */
    /*
    while (set.hasNext()) {
      System.out.println(set.next());
      //result = (Address)set.next();
    }
    */
    
    
    
    
    
    /*
    result.setPostalCode("2578");
    
    db.set(result);
    */
  
    
    
    db.close();
    
  
  }
  
  
  public String getCity() {
    return city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getStreet() {
    return street;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public void setStreet(String street) {
    this.street = street;
  }
  
  

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
    
  }

  public Map getTestMap() {
    return testMap;
  }

  public void setTestMap(Map testMap) {
    this.testMap = testMap;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
