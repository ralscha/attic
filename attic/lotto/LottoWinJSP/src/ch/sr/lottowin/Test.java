package ch.sr.lottowin;


import ch.sr.lottowin.db.*;
import com.objectmatter.bsf.*;

public class Test {

  

  public static void tip() {
    try {
      Server server = new Server("lottowin.schema");
      Database db = server.getDatabase();

    
      OQuery query = new OQuery(User.class);
      query.add("sr", "userid");
      User[] user = (User[])query.execute(db);
      System.out.println(user.length);
      if (user != null) {
        Tip[] tips = user[0].getTips();

        long id = 0;
        if (tips != null) {
          for (int i = 0; i < tips.length; i++) {
            if (id < tips[i].getId().longValue())
              id = tips[i].getId().longValue();
          }
        }
        id++;

        Tip newTip = (Tip)db.create(Tip.class); 
        newTip.setId(new Long(id));
        newTip.setZ1(new Long(1));
        newTip.setZ2(new Long(2));
        newTip.setZ3(new Long(3));
        newTip.setZ4(new Long(4));
        newTip.setZ5(new Long(5));
        newTip.setZ6(new Long(6));
        user[0].addTips(newTip);
        db.update(user[0]);
      }
      
      db.close();
      
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    }
  }

  public static void t2() {
    try {
      Server server = new Server("lottowin.schema");
      Database db = server.getDatabase();
      
      User user = (User)db.lookup(User.class, "demo");
      
      for (int i = 0; i < 10; i++) {
        Tip newTip = (Tip)db.create(Tip.class);
        newTip.setZ1(new Long(1));
        newTip.setZ2(new Long(2));
        newTip.setZ3(new Long(3));
        newTip.setZ4(new Long(4));
        newTip.setZ5(new Long(5));
        newTip.setZ6(new Long(6));
        newTip.setId(new Long(1999+i));
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
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
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