package ch.sr.lotto;

import java.util.*;

import ch.sr.lotto.db.*;
import com.objectmatter.bsf.*;

public class Test {

  public static void main(String[] args) {    
    try {
      Server server = new Server("lotto");
      Database db = server.getDatabase();
       
      Draw newDraw = new Draw();
      newDraw.setNo(new Integer(1));
      newDraw.setYear(new Integer(2002));
      newDraw.setNum1(new Integer(1));
      newDraw.setNum2(new Integer(2));
      newDraw.setNum3(new Integer(3));
      newDraw.setNum4(new Integer(4));
      newDraw.setNum5(new Integer(5));                        
      newDraw.setNum6(new Integer(6));
      newDraw.setAddnum(new Integer(10));
      newDraw.setJoker("111111");
      Calendar cal = new GregorianCalendar();
      newDraw.setDrawdate(cal.getTime()); 
      
      db.insert(newDraw);                             
      
      Draw[] draws = (Draw[])db.list(Draw.class);
      System.out.println(draws[0].getNo());

      db.delete(draws[0]);
      
      db.close();
    } catch (ClassNotFoundException e) {
      System.err.println(e);
    }
  
  }
}
