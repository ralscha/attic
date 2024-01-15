
import java.util.*;
import cmp.business.*;
import gnu.regexp.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.zip.*;
import visualnumerics.math.*;

public class Tests {

  /*
  private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
  private final static SimpleDateFormat sTimeFormat = new SimpleDateFormat("H:m");
  private final static SimpleDateFormat sDateFormat = new SimpleDateFormat("d.M.y");
  */

  public static void main(String args[]) {

 

    /*
    int something = 2;
    long startTime = System.currentTimeMillis();
    for (int i = 0, n=Integer.MAX_VALUE; i < n; i++) {
      something = -something;
    }
    long midTime = System.currentTimeMillis();
    for (int i = Integer.MAX_VALUE-1; i>=0; i--) {
      something = -something;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("inc  " + (midTime - startTime));
    System.out.println("dec  " + (endTime - midTime));
    */

    //System.out.println("haüß".toUpperCase());

    /*
     boolean ok = true;
    
     for (int i = 1; i <= 1766; i++) {
       int totalPages = 1766;
       int maxIndexPages = 17;
       int currentPage = i;
       int countPage = 1;
    
       if (totalPages > maxIndexPages) {
           countPage = currentPage - (maxIndexPages / 2);
           if (countPage < 1)
             countPage = 1;
    
           if ((totalPages - countPage + 1) < maxIndexPages)
             countPage -= maxIndexPages - (totalPages - countPage + 1);
    
           if (totalPages >= (countPage + maxIndexPages))
             totalPages = countPage + maxIndexPages - 1;
         }
    
       if ((totalPages - countPage) == (maxIndexPages - 1))
         ok = true;
       else {
         System.out.println("NOK");
         System.exit(1);
       }
       for (; countPage <= totalPages; countPage++) {
         System.out.print(countPage);
         if (countPage == currentPage)
           System.out.print("c");
         System.out.print(" ");
       }
       System.out.println();
     }
     */

    /*
      Calendar startCal = new GregorianCalendar();
      Calendar endCal = new GregorianCalendar();
      endCal.add(Calendar.DATE, +6);
    
      BigDate startbd = new BigDate(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH),
                                    startCal.get(Calendar.DATE));
    
      BigDate endbd = new BigDate(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH),
                                    endCal.get(Calendar.DATE));
    
      System.out.println(dateFormat.format(startCal.getTime()));
      System.out.println(dateFormat.format(endCal.getTime()));
    
      int days = endbd.getOrdinal()-startbd.getOrdinal();
      int weeks = days / 7;
      int days2 = 0;
      if (weeks > 0)
        days2 = (weeks * 7) - 1;
    
      int result = weeks * 2;
    
      Calendar tmpCal = new GregorianCalendar();
      tmpCal.setTime(startCal.getTime());
      tmpCal.add(Calendar.DATE, days2);
      if (tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        result = result + 1;
    
      if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        result = result + 1;
      else if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        result = result + 2;
    
      System.out.println(result);
    */
    /*
    String onedate = "01.01.2000";
    String time = "1:05";
    try {
      Date d1 = dateFormat.parse(onedate);
      Date d2 = sDateFormat.parse(onedate);
      Date d3 = timeFormat.parse(time);
      Date d4 = sTimeFormat.parse(time);
    
      System.out.println("D1 = " + sDateFormat.format(d1));
      System.out.println("D2 = " + sDateFormat.format(d2));
      System.out.println("D3 = " + sTimeFormat.format(d3));
      System.out.println("D4 = " + sTimeFormat.format(d4));
    
  } catch (ParseException pe) {
      System.err.println(pe);
  }
    */
    /*
    	Random rand = new Random();
    	int win = 0;
    	int quiz = 0;
    	int loose = 0;
    
    	for (int x = 0; x < 100000; x++) {
    		int prize = rand.nextInt(3);
    		int choice = rand.nextInt(3);
    		for (int i = 0; i < 3; i++) {
    			if (i != choice && i!=prize){
     					quiz = i;
    			}
    		}
    
    		//Tor wechseln
    		for (int i = 0; i < 3; i++) {
    		  	if ((i != choice) && (i != quiz)) {
    				choice = i;
    				break;
    			}
    		}
    
    		//Tor öffnen
    		if (choice == prize)
    			win++;
    		else
    			loose++;
    	}
    
    	System.out.println("win:   " + win);
    	System.out.println("loose: " + loose);
    */
    /*
    int primi[];
    primi = new int[]{ 2, 5, 7, 9, 11 };
    System.out.println(primi.length);
    */
    /*
    BigDate testBD = new BigDate(2000, 05, 01);
    
    int testOrdinal = testBD.getOrdinal();
    System.out.println(testOrdinal);
    
    int testnthOrdinal = BigDate.ordinalOfnthXXXDay(1, 1, 2000, 05);
    
    System.out.println(testnthOrdinal);
    */
    
    /*
    try {
    
    
    Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
    
    ObjectOutputStream oos = new ObjectOutputStream(new DeflaterOutputStream(new FileOutputStream("sertest.ser"), deflater));
    //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sertest.ser"));
    
    String[][] s = { {"hallo", "hello"}, {"a", "b"}};

    oos.writeObject(s);
    oos.close();

    Inflater inflater = new Inflater();
    ObjectInputStream ois = new ObjectInputStream(new InflaterInputStream(new FileInputStream("sertest.ser"), inflater));
    
    String[][] a = (String[][])ois.readObject();
    System.out.println(a[0][1]);
    ois.close();

    } catch (Exception ioe) {
      System.err.println(ioe);
    }
    */


    /*
    Locale[] locals = Locale.getAvailableLocales();
    for (int i = 0; i < locals.length; i++) {
    	System.out.println(locals[i]);
  }
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    System.out.println(months.length);
    */

    /*
    try {
    	//Write
    
    	Map map = new HashMap();
    	for (int i = 0; i < 10; i++) {
    		map.put(new Integer(i), new SerObject(i));
    	}
    
    	FileOutputStream out = new FileOutputStream("test");
         ObjectOutputStream s = new ObjectOutputStream(out);
         s.writeObject(map);
    	out.close();
    
    	//Read
    	FileInputStream in = new FileInputStream("test");
    	ObjectInputStream is = new ObjectInputStream(in);
    	Map m = (Map)is.readObject();
    	in.close();
    
    	System.out.println(m.size());
    	Iterator it = m.values().iterator();
    	while(it.hasNext()) {
    		SerObject so = (SerObject)it.next();
    		System.out.println(so);
    	}
    
    
  } catch (Exception e) {
    	System.err.println(e);
  }
    */
    /*
    try {
    	ServerSocket server = new ServerSocket( 1234);
    	// Timeout nach 1 Minute
    	server.setSoTimeout( 60000 );
    	try {
    		Socket socket = server.accept();
    	} catch ( InterruptedIOException e ) {
    		System.err.println( "Timed Out after one minute" );
    	}
  } catch (Exception e) {
    	System.err.println(e);
  }
    */
    /*
    String test = "Dies ist ein sehr langer Text";
    StringCharacterIterator it = new StringCharacterIterator(test);
    
    for ( char c = it.current(); c != CharacterIterator.DONE; c = it.next() )
  {
    	System.out.print( c );
  }
    */

    /*
      GregorianCalendar cal = new GregorianCalendar();
    System.out.println(cal.get(Calendar.HOUR));
    System.out.println(cal.get(Calendar.MINUTE));
    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
    System.out.println(df.format(cal.getTime()));
    */
    /*
    Class clazz = SparseBitSet.class;
    String methodname = "set";
    try {
    	Method m = clazz.getDeclaredMethod(methodname, null );
    	System.out.println(m);
    
  } catch (NoSuchMethodException nsme) {
    	System.err.println(nsme);
  }
    */
    /*
    	for (int i = 0; i < m.length; i++) {
    		System.out.println(m[i]);
    	}
    */


    /*
    String nr = "10281/34938 ac +*%&/(){}[]\\?^ 3984";
    StringBuffer sb = new StringBuffer();
    
    for (int i = 0; i < nr.length(); i++) {
    
    	char c = nr.charAt(i);
    	if (Character.isLetterOrDigit(c))
    		sb.append(Character.toUpperCase(c));
  }
    nr = sb.toString();
    System.out.println(nr);
    */

    /*
    File[] roots = File.listRoots();
    for (int i = 0; i < roots.length; i++) {
    	System.out.println(roots[i].getPath());
  }
    */
/*
 
    try {
    
     System.setProperty("java.protocol.handler.pkgs",
          "com.sun.net.ssl.internal.www.protocol");
     java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    
    	URL url = new URL("https://www.pbroker.ch");
    
        URLConnection con = url.openConnection();
        //SSLException thrown here if server certificate is invalid
         InputStream is = con.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line = null;
         while ((line = br.readLine()) != null) {
          System.out.println(line);
         }
    
  } catch (Exception mue) {
    	System.err.println(mue);
  }

*/
    /*
    byte b = (byte)130;
    System.out.println(b);
    
    String date = "1999-07-28-13.19.07.182";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
    ParsePosition pos = new ParsePosition(0);
    Date d = sdf.parse(date, pos);
    System.out.println(d);
    
    
    
    String test = "ÄÖÜäöüß";
    System.out.println(test.toUpperCase());
    */

    /*
    BigDate birth = new BigDate(1971, 6, 23);
    BigDate today = BigDate.localToday();
    int diff = today.getOrdinal() - birth.getOrdinal();
    System.out.println(diff);
    */
    /*
    
    String test = "dfdfdfd[<A HREF=bdsm2.htm>&gt;</A>]  erer";
    String test2 = "Ralph";
    try {
    	RE regex = new RE("\\[<(A) HREF=(\\w+[.]htm)>&gt;</A>\\]");
    	REMatch match = regex.getMatch(test);
    	if (match != null) {
    		for (int i = 0; i <= regex.getNumSubs(); i++) {
    			System.out.println(i + ":" + match.toString(i));
    			System.out.println(match.getSubStartIndex(i));
    			System.out.println(match.getSubEndIndex(i));
    		}
    	} else {
    		System.out.println("no match");
    	}
  } catch (Exception e) {
    	System.err.println(e);
  }
    */

    //COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");

    /*
    COM.stevesoft.pat.Regex regex = new COM.stevesoft.pat.Regex("\\[<A HREF=(\\w+[.]htm)>&gt;</A>\\]");
    
    regex.search(test);
    
    if (regex.didMatch()) {
    	System.out.println(regex.stringMatched(1));
    
  } else {
    	System.out.println("not found");
  }
    */

    /*
    String acct = AccountFormat.format("0207- 203799-32-109", AccountFormat.INTERN);
    System.out.println(acct);
    */
    /*
    String newline = System.getProperty("line.separator");
    System.out.println(newline.length());
    */
  }

  /*
   private static Calendar getCalendar(String datestr, String timestr) {
   	Calendar tmpdate = new GregorianCalendar();
   	Date date = null;
   	try {
   		date = dateFormat.parse(datestr);
   	} catch (ParseException pe) { }
   	tmpdate.setTime(date);
  
   	Calendar tmptime = null;
   	if (timestr != null) {
   		Date time = null;
   		try {
   			time = timeFormat.parse(timestr);
   		} catch (ParseException pe) { }
   		tmptime = new GregorianCalendar();
   		tmptime.setTime(time);
   	}
  
  
   	Calendar cal;
   	if (tmptime != null) {
   		cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
   				                           tmpdate.get(Calendar.MONTH),
   													tmpdate.get(Calendar.DATE),
   													tmptime.get(Calendar.HOUR_OF_DAY),
   													tmptime.get(Calendar.MINUTE));
   	} else {
   		cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
   			                           tmpdate.get(Calendar.MONTH),
   												tmpdate.get(Calendar.DATE));
  
   	}
   	return cal;
   }
    */
}
