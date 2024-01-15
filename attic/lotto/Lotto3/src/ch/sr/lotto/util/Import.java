package ch.sr.lotto.util;

import java.io.*;
import java.text.*;
import java.util.*;

import ch.sr.lotto.db.*;
import com.objectmatter.bsf.*;

public class Import {

  private final static DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

  public static void main(String[] args) {

    try {
      BufferedReader br = new BufferedReader(new FileReader("ziehung.txt"));
      String line = null;

      Server server = ServerUtil.createServer();

      

      Database db = server.getDatabase();
      
      db.startTrans();
            
      Draw[] draws = (Draw[])db.list(Draw.class);
      for (int i = 0; draws != null && i < draws.length; i++) {
        db.delete(draws[i]);
      }
      db.commit();

      db.startTrans();            
      JokerGainQuota[] jgq = (JokerGainQuota[])db.list(JokerGainQuota.class);
      for (int i = 0; jgq != null && i < jgq.length; i++) {
        db.delete(jgq[i]);
      }
      db.commit();
      
      db.startTrans();            
      LottoGainQuota[] lgq = (LottoGainQuota[])db.list(LottoGainQuota.class);
      for (int i = 0; lgq != null && i < lgq.length; i++) {
        db.delete(lgq[i]);
      }
      db.commit();
            

      db.startTrans();

      
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, ";");

        Draw newDraw = (Draw)db.create(Draw.class);

        newDraw.setNo(new Integer(st.nextToken()));
        newDraw.setYear(new Integer(st.nextToken()));
        
        String datestr = st.nextToken();
        Date drawDate = format.parse(datestr);         
        newDraw.setDrawdate(drawDate);

        
        newDraw.setNum1(new Integer(st.nextToken()));
        newDraw.setNum2(new Integer(st.nextToken()));
        newDraw.setNum3(new Integer(st.nextToken()));
        newDraw.setNum4(new Integer(st.nextToken()));
        newDraw.setNum5(new Integer(st.nextToken()));
        newDraw.setNum6(new Integer(st.nextToken()));
        newDraw.setAddnum(new Integer(st.nextToken()));
        
        if (st.hasMoreTokens()) {
          newDraw.setJoker(st.nextToken());
        }
        

        db.insert(newDraw);
      }
      
      db.commit();
      
      br.close();
      
      br = new BufferedReader(new FileReader("lottogewinnquoten.txt"));
      line = null;
      db.startTrans();
      
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, ";");

        LottoGainQuota newLgq = (LottoGainQuota)db.create(LottoGainQuota.class);
        newLgq.setNo(new Integer(st.nextToken()));
        newLgq.setYear(new Integer(st.nextToken()));
        
        String jackpot = st.nextToken();
        if (jackpot.equals("0.0")) {
          newLgq.setJackpot(null);
        } else {
          newLgq.setJackpot(new Double(jackpot));
        }
        
        newLgq.setNum6(new Integer(st.nextToken()));
        newLgq.setQuota6(new Double(st.nextToken()));

        newLgq.setNum5plus(new Integer(st.nextToken()));
        newLgq.setQuota5plus(new Double(st.nextToken()));
        newLgq.setNum5(new Integer(st.nextToken()));
        newLgq.setQuota5(new Double(st.nextToken()));
        newLgq.setNum4(new Integer(st.nextToken()));
        newLgq.setQuota4(new Double(st.nextToken()));
        newLgq.setNum3(new Integer(st.nextToken()));
        newLgq.setQuota3(new Double(st.nextToken()));
        
        
        
        
        
        db.insert(newLgq);
      }      
      
      db.commit();

      br.close();
      
      br = new BufferedReader(new FileReader("jokergewinnquoten.txt"));
      line = null;
      db.startTrans();
      
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, ";");

        JokerGainQuota newJgq = (JokerGainQuota)db.create(JokerGainQuota.class);
        newJgq.setNo(new Integer(st.nextToken()));
        newJgq.setYear(new Integer(st.nextToken()));
        
        String jackpot = st.nextToken();
        if (jackpot.equals("0.0")) {
          newJgq.setJackpot(null);
        } else {
          newJgq.setJackpot(new Double(jackpot));
        }
        
        newJgq.setNum6(new Integer(st.nextToken()));
        newJgq.setQuota6(new Double(st.nextToken()));

        newJgq.setNum5(new Integer(st.nextToken()));
        newJgq.setQuota5(new Double(st.nextToken()));
        newJgq.setNum4(new Integer(st.nextToken()));
        newJgq.setQuota4(new Double(st.nextToken()));
        newJgq.setNum3(new Integer(st.nextToken()));
        newJgq.setQuota3(new Double(st.nextToken()));
        newJgq.setNum2(new Integer(st.nextToken()));
        newJgq.setQuota2(new Double(st.nextToken()));                                
        
        db.insert(newJgq);
      }      
      
      db.commit();


      br.close();
      
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}