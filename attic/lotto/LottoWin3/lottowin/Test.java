package lottowin;

import com.objectmatter.bsf.*;
import java.io.*;
import lottowin.db.*;
import lottowin.form.*;
import lottowin.resource.*;
import java.util.*;
import java.text.*;

public class Test {

  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  public static void tip() {
    try {
      Database db = new Database("lottowin.schema");
      db.open();
    
      OQuery query = new OQuery(User.class);
      query.add("sr", "userid");
      User[] user = (User[])query.execute(db);
      System.out.println(user.length);
      if (user != null) {
        Tip[] tips = user[0].getTips();

        int id = 0;
        if (tips != null) {
          for (int i = 0; i < tips.length; i++) {
            if (id < tips[i].getId())
              id = tips[i].getId();
          }
        }
        id++;

        Tip newTip = (Tip)db.create(Tip.class); 
        newTip.setId(id);
        newTip.setZ1(1);
        newTip.setZ2(2);
        newTip.setZ3(3);
        newTip.setZ4(4);
        newTip.setZ5(5);
        newTip.setZ6(6);
        user[0].addTips(newTip);
        db.update(newTip);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    }
  }

  public static void t2() {
    Database db = AppConfig.getDatabase();
    User user = (User)db.lookup(User.class, "sr");
 
    for (int i = 0; i < 10; i++) {
      Tip newTip = (Tip)db.create(Tip.class);
      newTip.setZ1(1);
      newTip.setZ2(2);
      newTip.setZ3(3);
      newTip.setZ4(4);
      newTip.setZ5(5);
      newTip.setZ6(6);
      newTip.setId(700+i);
      try {
        user.addTips(newTip);      
      } catch (Exception e) {
        System.err.println(e);
      }
    }
    try {
      db.update(user);
    } catch (Exception e) {
      System.err.println(e);
    }
    System.out.println(user.getTips().length);
  }

	public static void main(String[] args) {
    //tip();
    t2();
    //CheckForm f = new CheckForm();

    /*
		try {
      Database db = new Database("lottowin.schema");
      db.open();

      BufferedReader br = new BufferedReader(new FileReader("ziehung.txt"));
      String line;
      while((line = br.readLine()) != null) {
        if (line.length() < 30) continue;
        Draw newDraw = (Draw)db.create(Draw.class); 
        newDraw.setNumber(Integer.parseInt(line.substring(0,3).trim()));
        newDraw.setDrawyear(Integer.parseInt(line.substring(5,9)));
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
      }


      db.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    }
    */
	
	}
}