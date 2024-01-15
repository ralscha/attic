package lottowin;

import com.objectmatter.bsf.*;
import java.io.*;
import lottowin.db.*;
import java.util.*;
import java.text.*;
import org.log4j.*;

public class UpdateDraw {

  private static Category CAT = Category.getInstance(UpdateDraw.class.getName());
  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  public static void main(String[] args) {
    if (args.length == 1) {
      tip(args[0]);
    } else {
      System.out.println("java UpdateDraw <file>");
    }
  }

  public static void tip(String file) {
    Database db = new Database("lottowin.schema");;
    try {
      db.open();

      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      while((line = br.readLine()) != null) {
        if (line.length() < 30) continue;

        int number = Integer.parseInt(line.substring(0,3).trim());
        int drawyear = Integer.parseInt(line.substring(5,9));
        
        OQuery query = new OQuery(Draw.class);
        query.add(number, "number");
        query.add(drawyear, "drawyear");
  
        Draw[] draw = (Draw[])query.execute(db);
        if ((draw == null) || (draw.length == 0)) {
          try {
            if (CAT.isDebugEnabled()) {
              CAT.debug("insert : " + number + "/" + drawyear);
            }
            Draw newDraw = (Draw)db.create(Draw.class); 
            newDraw.setNumber(number);
            newDraw.setDrawyear(drawyear);
            newDraw.setDrawdate(dateFormat.parse(line.substring(11,21)));
            newDraw.setZ1(Integer.parseInt(line.substring(24,26).trim()));
            newDraw.setZ2(Integer.parseInt(line.substring(28,30).trim()));
            newDraw.setZ3(Integer.parseInt(line.substring(32,34).trim()));
            newDraw.setZ4(Integer.parseInt(line.substring(36,38).trim()));
            newDraw.setZ5(Integer.parseInt(line.substring(40,42).trim()));
            newDraw.setZ6(Integer.parseInt(line.substring(44,46).trim()));
            newDraw.setZz(Integer.parseInt(line.substring(50,52).trim()));    
            if (newDraw.getDrawyear() >= 1997) {
              newDraw.setJoker(line.substring(55,61));
            }          
            db.insert(newDraw);
          } catch (ParseException pe) {
            CAT.error("insert(newDraw)", pe);
            System.err.println(pe);
          } catch (BOUpdateConflictException bce) {
            CAT.error("insert(newDraw)", bce);
            System.err.println(bce);
          }
        }
        
      }

      CAT.debug("updateDraw finished");
    } catch (Exception e) {
      CAT.error("updateDraw", e);
      System.err.println(e);
    } finally {
      db.discardAll();
      db.close();
      db = null;
    }
	
	}
}