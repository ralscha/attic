package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class ZiehungenTxtProducer extends Producer {

  private static final String htmlFile = "ziehung.txt";

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    StringBuffer sb = new StringBuffer();

    int numbers[];

    try {
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));
      
      OQuery query = new OQuery();
      query.addOrder("year");
      query.addOrder("no");

      Draw[] draws = (Draw[])db.get(Draw.class, query);
                  
      boolean showdate = false;

      for (int i = 0; i < draws.length; i++) {
        Draw draw = draws[i];
        
        sb.setLength(0);        

        if ((draw.getNo().intValue() == 1) && (draw.getYear().intValue() == 1997)) {
          showdate = true;
        }

        if (draw.getNo().intValue() < 100)
          sb.append(' ');
        if (draw.getNo().intValue() < 10)
          sb.append(' ');
        sb.append(draw.getNo()).append("  ");
        sb.append(draw.getYear()).append("  ");

        if (showdate)
          sb.append(dateFormat.format(draw.getDrawdate()));
        else
          sb.append("          ");

        sb.append("  ");
        numbers = draw.getNumbers();
        for (int j = 0; j < 6; j++) {
          if (numbers[j] < 10)
            sb.append(' ');
          sb.append(' ').append(numbers[j]).append(' ');
        }
        if (draw.getAddnum().intValue() < 10) {
          sb.append(' ');
        }
        
        sb.append("   ").append(draw.getAddnum());

        
  
        if (draw.getJoker() != null) {
          sb.append("   ");
          sb.append(draw.getJoker());
        }

        if (draw.getExtrajoker() != null) {
          sb.append("   ");
          sb.append(draw.getExtrajoker());
        }
        
        
        out.println(sb.toString());
      }

      out.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}