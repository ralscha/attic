package lotto.page;

import common.util.*;
import java.text.*;
import java.util.*;
import lotto.*;

public abstract class PageProducer {

	protected String templatePath;
	protected String lottoPath;
	protected String lottoBinPath;
	protected String lottoFramePath;
	protected String quotenPath;
	protected String quotenFramePath;
	protected String includePath;
	
	protected static String MONTHS[] = {"Januar", "Februar", "M&auml;rz", "April", "Mai", "Juni",
	                         "Juli", "August", "September", "Oktober", "November", "Dezember"};
	
	protected static String TITLES[] = {"alle Ziehungen", "ab Ziehung Nr. 14/1979", "ab Ziehung Nr. 1/1986",
	                    "ab Ziehung Nr. 1/1997", "aktuelles Jahr"};
	protected static String DESCRIPTIONS[] = {" ", " (Einf&uuml;hrung von 41 und 42)", " (Einf&uuml;hrung von 43, 44 und 45)",
	                    " (Einf&uuml;hrung Mittwochsziehung)", "(1999)"};

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public PageProducer() {
		lottoPath = AppProperties.getStringProperty("path.html.output");
		lottoBinPath = AppProperties.getStringProperty("path.html.output.bin");
		lottoFramePath = AppProperties.getStringProperty("path.html.output.frame");
		quotenPath = AppProperties.getStringProperty("path.html.output.quoten");
		quotenFramePath = AppProperties.getStringProperty("path.html.output.quoten.frame");
		includePath = AppProperties.getStringProperty("path.html.output.include");
		templatePath = AppProperties.getStringProperty("path.template");
	}
		
	public abstract void producePage(Ausspielungen auss, Properties props) ;

}