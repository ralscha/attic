
import COM.jbound.ecf.*;
import com.ibm.math.*;
import java.util.*;

public class ArrayTest {

  public static void stringTest() {
      long start = System.currentTimeMillis();
      for (int i = 0; i < 100000; i++) {
        String s1 = "Testing String";
        String s2 = "Concatenating Performance";
        String s3 = s1 + "  " + s2;      
      }		
      System.out.println(System.currentTimeMillis()-start);
  
      start = System.currentTimeMillis();
      for (int i = 0; i < 100000; i++) {
        StringBuffer s = new StringBuffer();
        s.append("Testing String");
        s.append(" ");
        s.append("Concatenating Performance");
        String s3 = s.toString();
      }		
      System.out.println(System.currentTimeMillis()-start);

      start = System.currentTimeMillis();
      for (int i = 0; i < 100000; i++) {
        StringBuffer s = new StringBuffer(45);
        s.append("Testing String");
        s.append(" ");
        s.append("Concatenating Performance");
        String s3 = s.toString();
      }		
      System.out.println(System.currentTimeMillis()-start);


  }


	public static void main(String[] args) {
    stringTest();
    System.exit(0);

    boolean c = false;
    if (args.length == 1)
	    c = "0".equalsIgnoreCase(args[0]);

		// Execute the method
		List l = new ArrayList();
    for (int i = 0; i < 10; i++) {
      l.add(String.valueOf(i));
    }

    for (int n = 0; n < 10; n++) {
      long start, stop;		
      if (c) {
		    start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
          String[] items = (String[])l.toArray(new String[0]); 
        }
		    stop = System.currentTimeMillis();
      } else {
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
          String[] items = (String[])l.toArray(new String[l.size()]); 
        }
        stop = System.currentTimeMillis();        
      }
		  // Display elapsed time
		  System.out.println((stop - start) + "ms");
    }
	}

}