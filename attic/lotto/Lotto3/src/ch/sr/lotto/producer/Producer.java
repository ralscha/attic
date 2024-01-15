package ch.sr.lotto.producer;

import java.text.*;
import java.util.*;

import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public abstract class Producer {

  protected static String templatePath;
  protected static String lottoPath;
  protected static String lottoBinPath;
  protected static String quotenPath;
  protected static String includePath;


  protected static String MONTHS[] = { "Januar", "Februar", "M&auml;rz", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };

  protected static String TITLES[] = { "alle Ziehungen", "ab Ziehung Nr. 14/1979", "ab Ziehung Nr. 1/1986", "ab Ziehung Nr. 1/1997", "aktuelles Jahr" };
  protected static String DESCRIPTIONS[] = { " ", " (Einf&uuml;hrung von 41 und 42)", " (Einf&uuml;hrung von 43, 44 und 45)", " (Einf&uuml;hrung Mittwochsziehung)", "(1999)" };

  protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  static {
    lottoPath = AppProperties.getStringProperty("path.html.output");
    lottoBinPath = AppProperties.getStringProperty("path.html.output.bin");
    quotenPath = AppProperties.getStringProperty("path.html.output.quoten");
    includePath = AppProperties.getStringProperty("path.html.output.include");
    templatePath = AppProperties.getStringProperty("path.template");
  
  
    try {
      Velocity.init();
    } catch (Exception e) {
      System.err.println(e);
    }
    
    

  }

  public Producer() {  
    //no action
  }

  public abstract void producePage(Database db, Properties props, AusspielungenStatistiken as);

}