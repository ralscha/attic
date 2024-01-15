
import java.util.*;
import java.io.*;
import common.util.*;
import common.util.log.*;
import common.io.*;
import lotto.*;
import lotto.extract.*;
import java.util.zip.*;
import com.ibm.webrunner.net.FTPSession;  
import lotto.util.*;


public class Tests {
	
	public void run16() {
		
		System.out.println((int)'a');
		char c1 = 'ü';
		char c2 = '&';
		char c3 = '.';
		
		System.out.println(Character.isLetter(c1));
		System.out.println(Character.isLetter(c2));
		System.out.println(Character.isLetter(c3));
		
		/*
		Date today = new Date();
		
		Calendar calc = new GregorianCalendar();
		calc.setTime(today);
		calc.add(Calendar.DATE, -1);
		
		today = calc.getTime();
		System.out.println(today);
		*/
	
	}
	
	
	
	
	public void run14() {
		String line = "2,1999,09.01.1999,17,23,24,35,37,43,21,007607";
		StringTokenizer st = new StringTokenizer(line, ",");
		while (st.hasMoreTokens()) {
			System.out.println(st.nextToken());
		}
	}
	
	public void run13() {
		PercentCalculator pc = new PercentCalculator();
		pc.add(10);
		pc.add(3);
		pc.add(17);
		pc.add(5);
		System.out.println(pc.getPercentString(10));
		System.out.println(pc.getPercentString(3));
		System.out.println(pc.getPercentString(17));
		System.out.println(pc.getPercentString(5));
		
	}
	
	public void run12() {
		
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -10);
		System.out.println(today.get(Calendar.DATE));
		System.out.println(today.get(Calendar.MONTH));
		System.out.println(today.get(Calendar.YEAR));

		
	}
	
	public void run11() {
		
		String ok = "19981231";
		String n  = "19990101";
		String b  = "19980623";
		String e  = "19981231";
		
		System.out.println(ok.compareTo(n));
		System.out.println(ok.compareTo(b));
		System.out.println(ok.compareTo(e));		
	}
	
	
	public void run9() {
		try {
			TemplateWriter tw = new TemplateWriter();
			tw.addFile("d:/projects/lotto3/template/aktuelle.template");
			tw.addVariable("nr", 10);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
			tw.write(pw);
			pw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void run8() {
		FTPSession session = null;
		try {
			session = new FTPSession(); 
			
			session.setServer("ftp.tripod.com"); 
			session.setUser("ralph_schaer"); 
			session.setPassword("hybhnp");		
			session.logon();
			System.out.println("Logged on");
			session.changeDirectory("/backup");
			System.out.println("Change directory");
			try {
				session.deleteFile("Backup.zip");
			} catch (IOException ioe) { }
			System.out.println("delete File");
			session.storeFile("d:/temp/backup.zip", "backup.zip", 'I');
			System.out.println("store File");
			
			session.logoff(); 
		} catch (IOException ioe) {
			if (session.getLoggedOn()) {
				try {
					session.logoff();
				} catch (IOException io) { }
			}
			
			System.err.println(ioe);
		}
	}
	
	
	public void run4() {
		AppProperties.setFileName("d:/projects/Lotto3/lotto.props");
		Extractor ex = new Extractor_Teletext();
		
		Calendar cal = new GregorianCalendar(1999, Calendar.MARCH, 13);
		Ziehung zie = ex.extractZiehung(cal);
		LottoGewinnquote lg = ex.extractLottoGewinnquote(cal);
		JokerGewinnquote jg = ex.extractJokerGewinnquote(cal);
		
		zie.showSlotName = true;
		lg.showSlotName = true;
		jg.showSlotName = true;
		
		System.out.println(zie);
		System.out.println(lg);
		System.out.println(jg);
	}
		
	public void run2() {
		Log.setLogger(new LineLimitDiskLogger("d:/temp/"));
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < 100000; i++) {
			Log.log("TEST");
		}
		
		System.out.println("Millis : " + (System.currentTimeMillis() - start));
		
		Log.log("Ende Feuer");
		Log.cleanUp();
	}
	
	
	public void run() {
		
		AppProperties.setFileName("d:/projects/Tests/Tests.dat");
		
		/*
		boolean b = false;
		AppProperties.putBooleanProperty("isOn", b);
		
		double d = 1.3439;
		AppProperties.putDoubleProperty("pi", d);
		
		int i = 1234;
		AppProperties.putIntProperty("iq", i);
		
		String file = "c:/hallo/hall.txt";
		AppProperties.putStringProperty("txtFile", file);
		
		AppProperties.store();
		*/
		
		System.out.println("isOn = " + AppProperties.getBooleanProperty("isOn"));
		System.out.println("pi   = " + 	AppProperties.getDoubleProperty("pi"));
		System.out.println("iq   = " + AppProperties.getIntegerProperty("iq"));
		System.out.println("txtFile = " + AppProperties.getStringProperty("txtFile"));
		
		System.out.println("pi2  = " + AppProperties.getStringProperty("pi"));
		/*
		Set keys = AppProperties.getInstance().keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		*/
	}
	
	public static void main(String[] args) {
		new Tests().run16();
	}
}