package ch.sr.lotto;

import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.producer.*;
import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class TestProducer {

  public static void main(String[] args) {
    try {
      Server server = ServerUtil.createServer();
      Database db = server.getDatabase();

      AusspielungenStatistiken as = new AusspielungenStatistiken();
      OQuery query = new OQuery();
      query.addOrder("year");
      query.addOrder("no");

      Draw[] draw = (Draw[]) db.get(Draw.class, query);
      for (int i = 0; i < draw.length; i++) {
        as.addDraw(draw[i]);
      }

      //new LottoWinProducer().producePage(db, null, as);

      //new ZiehungenTxtProducer().producePage(db, null, as);
      //new AktuelleWMLProducer().producePage(db, null, as);
      //new AusstehendProducer().producePage(db, null, as);
      //new AusstehendWMLProducer().producePage(db, null, as);
      //new AktuelleProducer().producePage(db, null, as);
      //new DateProducer().producePage(db, null, as);
      //new SummeProducer().producePage(db, null, as);
      //new EndziffernSummeProducer().producePage(db, null, as);
      /*
      GeradeUngeradeChartsProducer gp =
        new GeradeUngeradeChartsProducer();
      gp.producePage(db, null, as);
      JFrame frame = gp.getFrame();
      WindowEvent windowClosingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
      frame.dispatchEvent(windowClosingEvent);
      */
      
      //new GeradeUngeradeProducer().producePage(db, null, as);
      //new HaeufigkeitProducer().producePage(db, null, as);
      //new Klasse3Producer().producePage(db, null, as);
      //new Klasse5Producer().producePage(db, null, as);
      //new Klasse9Producer().producePage(db, null, as);            
      
      //new PaarProducer().producePage(db, null, as);
      //new QuersummeProducer().producePage(db, null, as);
      //new TipsProducer().producePage(db, null, as);
      //new TiefeHoheEndziffernProducer().producePage(db, null, as);
      //new TiefeHoheProducer().producePage(db, null, as);
      //new SummeDifferenzProducer().producePage(db, null, as);
      //new VerbundProducer().producePage(db, null, as);
      Properties props = new Properties();
      /*
      props.put("asc", Boolean.FALSE);
      //props.put("year", new Integer(1998));
      props.put("withDate", Boolean.FALSE);
      props.put("joker", Boolean.FALSE);
      new ZiehungenProducer().producePage(db, props, as);
      

      new JokerGewinnquotenProducer().producePage(db, props, as);
      new LottoGewinnquotenProducer().producePage(db, props, as);
      */
      
      props.put("asc", Boolean.FALSE);
      props.put("year", new Integer(2003));

      new JokerGewinnquotenProducer().producePage(db, props, as); 
      new ExtraJokerGewinnquotenProducer().producePage(db, props, as);     
      new LottoGewinnquotenProducer().producePage(db, props, as);      

      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}