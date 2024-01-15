import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParseException;
import nu.xom.ValidityException;


public class Meggen {

  public static void main(String[] args) {
    try {
      File f = new File("D:\\eclipse\\workspace\\blankwebapp\\web\\WEB-INF\\web.xml");
   
   
      Builder parser = new Builder(false);
      
  
      Document doc = parser.build(f);
      
      
      System.out.println(doc.toString());
      
    } catch (FileNotFoundException e) {      
      e.printStackTrace();
    } catch (IOException e) {      
      e.printStackTrace();
    } catch (ValidityException e) {      
      e.printStackTrace();
    } catch (ParseException e) {      
      e.printStackTrace();
    }
  }

}
