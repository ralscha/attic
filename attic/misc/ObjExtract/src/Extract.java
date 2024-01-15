import java.io.FileNotFoundException;

import ch.ess.business.db.obj.MlBewertung;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.query.Query;


/**
 * @author sr
 */
public class Extract {

  public static void main(String[] args) {
    try {
      Db4o.licensedTo("ralphschaer@yahoo.com");
         
      ObjectContainer container = Db4o.openFile("D:\\eclipse\\workspace\\ObjExtract\\ct.yap");
  
      Statistics.main(new String[] {"D:\\eclipse\\workspace\\ObjExtract\\ct.yap"});
      
//      Query oquery = container.query();
//      oquery.constrain(MlBewertung.class);
//      
//    ObjectSet set = oquery.execute();
//    
//    
//    while (set.hasNext()) {
//      MlBewertung bewertung = (MlBewertung)set.next();  
//      System.out.println(bewertung);      
//    }
//    
//      PrintStream fos = new PrintStream(new FileOutputStream("c:/temp/output.txt"));
//      Logger.setOut(fos);
//      Logger.logAll(container);
//      fos.close();
      /*
      StoredClass[] classes = container.ext().storedClasses();

      // remove classes that are currently not available,
      // abstract classes and all second class objects
      for (int i = 0; i < classes.length; i++) {
        try {
          if (classes[i].getName().indexOf("MlBewertung") != -1) {
            long[] ids = classes[i].getIDs();
            for (int j = 0; j < ids.length; j++) {
              System.out.println(ids[j]);
            }
          
          }
          
          

        } catch (Throwable t) {
          classes[i] = null;
        }
      }*/
      

      container.close();
    } catch (DatabaseFileLockedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }
}
