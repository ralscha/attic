
import com.codestudio.util.*;
import ch.ess.calendar.session.*;
import ch.ess.calendar.db.*;
import ch.ess.calendar.xml.*;
import ch.ess.calendar.*;

import java.util.*;
import java.sql.*;
import java.io.*;

public class Test {

	public static void main(String[] args) throws Exception{

		//WeekViewRequest weekRequest = new WeekViewRequest();
		
		AppointmentsCache appcache = new AppointmentsCache();

		appcache.init(new CategoriesMap());
		
		WeekViewRequest week = new WeekViewRequest();
		week.setUserid("sr");
		week.setAppCache(appcache, true, "sr");
		
		
		for (int i = 0; i < 7; i++) {
			List alldaylist = week.getAlldayAppointments(i);
			List timedlist  = week.getTimedAppointments(i);
			
			System.out.println(alldaylist);
			System.out.println(timedlist);	
		}	
		
		/*
		File f = new File("outlook.xml");
		FileInputStream is = new FileInputStream("outlook.xml");

		File dir = new File("d:/javaprojects/ess/planner/java_nt/");
		File fout = File.createTempFile("appointment", ".xml", dir);
		//fout.deleteOnExit();
		
		FileOutputStream fos = new FileOutputStream(fout);
		
		ch.ess.calendar.util.StreamCopier.copy(is, fos);
		is.close();
		fos.close();
		
		
		Lax lax = new Lax();

		AppointmentsHandler appHandler = new AppointmentsHandler();
		lax.addHandler(appHandler);

		lax.parseDocument(false, lax, fout);
				
		fout.delete();
		*/
	}
}