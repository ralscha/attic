package testset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSet {

  public static void main(String[] args) {
    Set hash = new HashSet();
        
    hash.add(new TestBean(new Long(1)));
    hash.add(new TestBean());
    hash.add(new TestBean(new Long(2)));
    hash.add(new TestBean(new Long(1)));
    
    for (Iterator it = hash.iterator(); it.hasNext();) {
      System.out.println(it.next());
      
    }
    
    hash.remove(new TestBean());
    System.out.println("##");
    for (Iterator it = hash.iterator(); it.hasNext();) {
      System.out.println(it.next());
      
    }
  }
}
