package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class LottoWinProducer extends Producer {

  private static final String htmlFile = "lottowin.data";

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    StringBuffer sb = new StringBuffer(100);

    int numbers[];

    try {
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));
      
      OQuery query = new OQuery();
      query.add(2001, "year", OQuery.GREATER);
      query.addOrder("year");
      query.addOrder("no");

      Draw[] draws = (Draw[])db.get(Draw.class, query);
      
                  
      for (int i = (draws.length-31); i < draws.length; i++) {
        sb.setLength(0);
        Draw z = draws[i];
        
        sb.append(z.getNo());
        sb.append(';');
        sb.append(z.getYear());
        sb.append(';');
        if (z.isWednesday())
          sb.append("Mi, ");
        else
          sb.append("Sa, ");
        sb.append(dateFormat.format(z.getDrawdate()));
        sb.append(';');
        
        numbers = z.getNumbers();
        for (int j = 0; j < 6; j++) {
          sb.append(numbers[j]);
          sb.append(';'); 
        }
        
        sb.append(z.getAddnum());
        sb.append(';');
        sb.append(z.getJoker());
        out.println(sb.toString());
        
      

      }

      out.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}