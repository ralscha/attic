import java.util.HashMap;
import java.util.Map;


public class Person {

  private static Map registry = new HashMap();

  public final static String LINIE = "L";
  public final static String KST = "K";
  public final static String EINKAUF = "E";
  public final static String GL = "G";

  private Integer id;
  private String name;
  private String funktion;
  private Person parent;
  
  private Person() {
  }
  
  public Person(int id, String name, String funktion) {
    this(id, name, funktion, new Integer(-1));
  }
  
  public Person(int id, String name, String funktion, int parent) {
    this(id, name, funktion, new Integer(parent));
  }  
  
  public Person(int id, String name, String funktion, Integer parent) {
    this.id = new Integer(id);
    this.name = name;
    this.funktion = funktion;
    
    if (parent.intValue() == -1) {
      this.parent = null;
    } else {
      Person p = (Person)registry.get(parent);
      if (p == null) {
        throw new IllegalArgumentException("parent not found");
      }
      this.parent = p;      
    }    
    
    registry.put(this.id, this);
  }
  
  public String getFunktion() {
    return funktion;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Person getParent() {
    return parent;
  }

  public void setFunktion(String string) {
    funktion = string;
  }

  public void setId(Integer integer) {
    id = integer;
  }

  public void setName(String string) {
    name = string;
  }

  public void setParent(Person parent) {
    this.parent = parent;
  }

  public static Map getRegistry() {
    return registry;
  }
  
  public static Person lookupPerson(int id) {    
    return (Person)registry.get(new Integer(id));  
  }

}
