import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import java.util.*;


public class Test {

  private static List getStringParts(String str, int len) {
    if (str == null) {
      return null;
    }
    
    
    List result = new ArrayList();
    while(str.length() >= len) {
      String part = str.substring(0, len);
      result.add(part);
      str = str.substring(len);
    }    
    result.add(str);          
    
    return result;
    
  }

  public static void main(String[] args) {
    
    String input = "einsehrlangerstringoderauchnicht1saasfdsafasfasfasdf";
    List result = getStringParts(input, 200);
    String test = "";
    for (Iterator it = result.iterator(); it.hasNext();) {
      String k = (String)it.next();
      test += k;
    }
    
    System.out.println(test.equals(input));
 
    /*
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("PST"));
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      //System.out.println(cal.getTimeInMillis());
      
      
      cal.setTimeZone(TimeZone.getTimeZone("GMT"));
      
      cal.set(Calendar.HOUR_OF_DAY, 23);
      //System.out.println(cal.getTimeInMillis());
      //System.out.println(cal.getTime());
    }    
    System.out.println((System.currentTimeMillis() - start) + " ms");
    */
    /*
    Calendar cal = Calendar.getInstance();
    System.out.println(cal.getTime());
    
    Calendar cal2 = new GregorianCalendar(2003, Calendar.DECEMBER, 31, 23, 59, 59);    
    
    cal2.setTimeZone(TimeZone.getTimeZone("UTC"));
    */
    /*
    cal2.set(Calendar.YEAR, 2003);
    cal2.set(Calendar.MONTH, Calendar.DECEMBER);
    cal2.set(Calendar.DATE, 31);
    cal2.set(Calendar.HOUR_OF_DAY, 23);
    cal2.set(Calendar.MINUTE, 59);
    cal2.set(Calendar.SECOND, 59);
    */
    /*
    //System.out.println(cal2.get(Calendar.HOUR_OF_DAY));
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    System.out.println(format.format(cal2.getTime()));
    */
    /*
    
    Calendar cal = new GregorianCalendar(2002, Calendar.JANUARY, 12);
    Recurrance r = new Recurrance("FREQ=YEARLY;INTERVAL=1;BYMONTH=5;BYDAY=2SU", cal.getTime());
    
    Calendar from = new GregorianCalendar(2003, Calendar.JANUARY, 1);
    Calendar to = new GregorianCalendar(2003, Calendar.DECEMBER, 31);
    
    List l = r.getAllMatchingDatesOverRange(from.getTime(), to.getTime());
    for (Iterator iter = l.iterator(); iter.hasNext();) {
      System.out.println(iter.next());
      
    }*/
/*
    //int l = 0;
    Hook h = new Hook();
    for (int i = 1; i < 12; i++) {
      new Combination(new Object[] { "70", "70", "155", "90", "91", "82", "99", "101", "110", "120", "131", "140" }, i, h).combine();
    }
    System.out.println(h.getMaxLen());
  */
  }

}

class Hook implements CombinationHook {

  private int maxLen = 0;

  public void newCombination(Object[] result) {

    int len = 0;
    for (int i = 0; i < result.length; i++) {
      len += Integer.parseInt((String) result[i]);
    }
    if (len == 1000) {
      
      for (int i = 0; i < result.length; i++) {
        System.out.print(result[i]);
        System.out.print(" ");
      }
      System.out.println();
      
      System.out.println(len);
      System.exit(0);
    }
    if (len < 1000) {
      maxLen = Math.max(maxLen, len);
    }
  }

  public int getMaxLen() {
    return maxLen;
  }

}
