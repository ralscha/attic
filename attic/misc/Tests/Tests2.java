import java.util.*;
import common.util.log.*;
import java.text.*;

public class Tests2{


void run() {
 long t1, t2;
      int  actres, sumres = 0, i = 0;
      while (true) {
         ++i;
         t1 = System.currentTimeMillis();
         while (true) {
            t2 = System.currentTimeMillis();
            if (t2 != t1) {
               actres = (int)(t2 - t1);
               break;
            }
         }
         sumres += actres;
         System.out.print("it="+i+", ");
         System.out.print("actres="+actres+" msec., ");
         System.out.print("avgres="+(sumres/i)+" msec.");
         System.out.println("");
         try {
            Thread.sleep(500);
         } catch (InterruptedException e) {
            //nichts
         }
      }

}

	public void run1() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
			Calendar today = new GregorianCalendar();
			System.out.println(dateFormat.format(today.getTime()));
	}

	public void run2() {
		System.out.println(Math.floor(2.9));
		System.out.println(Math.floor(-2.9));
		System.out.println(Math.ceil(2.9));
		System.out.println(Math.ceil(-2.9));
	}

	public void run3() {

	}
	
	public void run4() {
		DecimalFormat df = new DecimalFormat("000");
		System.out.println(df.format(1));
		System.out.println(df.format(99));
		System.out.println(df.format(100));
	}

	public void run6() {
		
		SimpleDateFormat formatter = new SimpleDateFormat ("dd/MMM/yyyy:hh:mm:ss", Locale.US);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse("16/Jan/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Feb/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Mar/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Apr/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/May/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Jun/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Jul/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Aug/1999:09:02:04", pos);
		System.out.println(d);
				pos = new ParsePosition(0);
		 d = formatter.parse("16/Sep/1999:09:02:04", pos);
		System.out.println(d);		
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Oct/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Nov/1999:09:02:04", pos);
		System.out.println(d);
		pos = new ParsePosition(0);
		 d = formatter.parse("16/Dec/1999:09:02:04", pos);
		System.out.println(d);
	}
	
	public void run7() {
		COM.stevesoft.pat.Regex logRegex = 
		new COM.stevesoft.pat.Regex("\\[([^\\]]+)");
		
		String s1 = "pop-ls-07-3-dialup-79.freesurf.ch - - [10/Jul/1999:20:04:53 +0200] \"GET /rschaer/Lotto/frtop.html HTTP/1.1\" 200 456 \"http://www.datacomm.ch/rschaer/Lotto/\" \"Mozilla/4.0 (compatible; MSIE 4.01; Windows 95; sunrise free surf)\"";
		
		logRegex.search(s1);
		if (logRegex.didMatch()) {
			for (int i = 1; i <= logRegex.numSubs(); i++) 
				System.out.println(i + "-->" + logRegex.stringMatched(i));
		} else
			System.out.println("NO");
	}


	public static void main(String[] args) {
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		//dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
		
	
		System.out.println(dateFormat.format(new Date()));
		
		/*new Tests2().run();
		
		try {
		Class.forName("java.util.Date");
		System.out.println(Date.class);
		} catch (Exception e) { }
		*/
	}
}