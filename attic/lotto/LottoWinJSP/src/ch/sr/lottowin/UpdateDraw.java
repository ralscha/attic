package ch.sr.lottowin;

import java.io.*;
import java.text.*;

import org.apache.log4j.*;

import ch.sr.lottowin.db.*;
import com.objectmatter.bsf.*;

public class UpdateDraw {

  private static Category LOG = Category.getInstance(UpdateDraw.class.getName());
  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  public static void main(String[] args) {
    if (args.length == 1) {
      tip(args[0]);
    } else {
      System.out.println("java UpdateDraw <file>");
    }
  }

  public static void tip(String file) {
    try {
      
      DbPool.initPool("resource:/schrepository/lottowin.schema", "SQLSERVER", "com.inet.tds.TdsDriver", "jdbc:inetdae7:localhost:1433?database=lotto", "sa", "papa8gei");
      
      Database db = DbPool.requestDatabase();

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
            if (LOG.isDebugEnabled()) {
              LOG.debug("insert : " + number + "/" + drawyear);
            }
            Draw newDraw = (Draw)db.create(Draw.class); 

            newDraw.setNumber(new Long(number));
            newDraw.setDrawyear(new Long(drawyear));
            newDraw.setDrawdate(dateFormat.parse(line.substring(11,21)));
            newDraw.setZ1(new Long(line.substring(24,26).trim()));
            newDraw.setZ2(new Long(line.substring(28,30).trim()));
            newDraw.setZ3(new Long(line.substring(32,34).trim()));
            newDraw.setZ4(new Long(line.substring(36,38).trim()));
            newDraw.setZ5(new Long(line.substring(40,42).trim()));
            newDraw.setZ6(new Long(line.substring(44,46).trim()));
            newDraw.setZz(new Long(line.substring(50,52).trim()));    
            if (newDraw.getDrawyear().longValue() >= 1997) {
              newDraw.setJoker(line.substring(55,61));
            }          
            db.insert(newDraw);
          } catch (ParseException pe) {
            LOG.error("insert(newDraw)", pe);
            System.err.println(pe);
          } catch (BOUpdateConflictException bce) {
            LOG.error("insert(newDraw)", bce);
            System.err.println(bce);
          }
        }
        
      }

      DbPool.returnDatabase(db);
      LOG.debug("updateDraw finished");
    } catch (Exception e) {
      LOG.error("updateDraw", e);
      System.err.println(e);
    }
	
	}
}