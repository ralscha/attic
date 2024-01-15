

public class TestPerson {
  public static void main(String[] args) {
  
    
    new Person(1, "Markus Einkauf", Person.EINKAUF);    
    
    new Person(2, "Bernhard GL", Person.GL, 1);    
    
    new Person(4, "Maria KST", Person.KST, 2);
    new Person(5, "Ernst KST", Person.KST, 4);
    new Person(6, "Frist KST", Person.KST, 5);
  
    new Person(7, "Hugo Einkauf", Person.EINKAUF, 6);
    
    
    new Person(3, "Andreas GL", Person.GL, 1);    
    
    new Person(8, "Udo KST", Person.KST, 3);
    new Person(9, "Gerhard KST", Person.KST, 8);    
  
    new Person(10, "George Einauf", Person.EINKAUF, 9);    
    
            
    Person p = Person.lookupPerson(10);
    
    while (p.getParent() != null) {
      System.out.println(p.getName());      
      p = p.getParent();
    }
    System.out.println(p.getName()); 
  }
}
