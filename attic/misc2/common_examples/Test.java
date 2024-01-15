
import java.util.*;
import common.util.*;
import java.io.*;

public class Test {
  
   public static void main(String[] args) {
   	try{
     TreeSet t = new TreeSet();
     for (int i = 0; i < 10; i++) {
       t.add(new Integer((int)(Math.random()*1000)));             
     }     
     
     ObjectInspector insp = new ObjectInspector();
     insp.addInclusionFilter("java.util.*");
     insp.output(t, new FileWriter("test.html"));
} catch (Exception e) {
	 System.err.println(e);
	}
   }

}