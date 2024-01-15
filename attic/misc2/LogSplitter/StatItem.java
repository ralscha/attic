

import java.util.*;
import java.text.*;
import org.apache.oro.text.regex.*;

public class StatItem {

  private static Pattern pattern;
	private static Perl5Compiler compiler;

  private static final String PATTERN_STRING = "^(\\d+);(\\d+);([^;]+);([^;]+);(\\d+)";

  public final static SimpleDateFormat dateFormat = 
  					new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);

  static {
  	compiler = new Perl5Compiler();

		try {
		  pattern = compiler.compile(PATTERN_STRING);
		} catch (MalformedPatternException e) {
		  System.err.println("Bad pattern.");
		  System.err.println(e.getMessage());
		}  
  }

  public final static int EMA_TYP = 1;
  public final static int KUNDE_TYP = 2;
  public final static int LIEFERANT_TYP = 3;
  public final static int PBROKER_TYP = 4;

  private Date inDate;  
  private Date outDate;

  private int typ;
  private int no;

  private int duration;

  //support
  Calendar calIn  = new GregorianCalendar();
  Calendar calOut = new GregorianCalendar();

  public int getNo() {
	return no;
  }  

  public void setNo(int no) {
	this.no = no;
  }  

  public int getTyp() {
	return typ;
  }  

  public void setTyp(int typ) {
	this.typ = typ;
  }  

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public Date getInDate() {
	return inDate;
  }  

  public void setInDate(Date date) {
	this.inDate = date;
  }  

  public Date getOutDate() {
	return outDate;
  }  

  public void setOutDate(Date date) {
	this.outDate = date;
  }  


  public static StatItem create(String line) {
    try {
  	  PatternMatcher matcher = new Perl5Matcher();
  	  if (matcher.contains(line, pattern)) {
		    MatchResult result = matcher.getMatch();

        StatItem si = new StatItem();
        si.setNo(Integer.parseInt(result.group(1)));
        si.setTyp(Integer.parseInt(result.group(2)));
        si.setInDate(dateFormat.parse(result.group(3)));
        si.setOutDate(dateFormat.parse(result.group(4)));
        si.setDuration(Integer.parseInt(result.group(5).trim()));

        return si;
  	  }
    } catch (ParseException pe) { }
    return null;
  }

  public void write(java.io.PrintWriter pw) {

  //wenn in und outdate nicht vom gleichen tag sind
  //nicht schreiben

  Calendar in = new GregorianCalendar();
  Calendar out = new GregorianCalendar();
  in.setTime(getInDate());
  out.setTime(getOutDate());

  int dayin = in.get(Calendar.DATE);
  int dayout = out.get(Calendar.DATE);

  int monthin = in.get(Calendar.MONTH);
  int monthout = out.get(Calendar.MONTH);

  if ((dayin == dayout) && (monthin == monthout)) {

	StringBuffer sb = new StringBuffer(100);
	sb.append(getNo()).append(";");
	sb.append(getTyp()).append(";");
	  sb.append(dateFormat.format(getInDate())).append(";");
	  sb.append(dateFormat.format(getOutDate())).append(";");

	long diff = (getOutDate().getTime() - getInDate().getTime()) / 1000;
	sb.append(diff);
	
	
	pw.println(sb.toString());
  }  
  }  
}