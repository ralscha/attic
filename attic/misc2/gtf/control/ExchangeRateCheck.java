package gtf.control;

import java.io.*;
import java.util.*;
import common.util.*;
import common.net.*;
import java.text.*;
import java.sql.*;

public class ExchangeRateCheck {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private List centers;
	
	private final static String selectDateSQL = "select max(rate_fixing_date) from gtflc.exchange_rate where iso_code_alpha = 'USD'";
	private final static String selectNoSQL = "select max(rate_fixing_number) from gtflc.exchange_rate where iso_code_alpha = 'USD' and rate_fixing_date in (select max(rate_fixing_date) from gtflc.exchange_rate where iso_code_alpha = 'USD')";
	
	public ExchangeRateCheck() {
		String c = AppProperties.getStringProperty("service.centers");		
		centers = new ArrayList();
		
		StringTokenizer st = new StringTokenizer(c, ",");
		while (st.hasMoreTokens())
			centers.add(st.nextToken());		

    	String dbDriver = AppProperties.getStringProperty("db.driver").trim();
    	try {
			Class.forName(dbDriver);
    	} catch (ClassNotFoundException cnfe) {
    		System.err.println(cnfe);
    	}
    	check();
	}
	
	public void check() {
		try {
			for (int i = 0; i < centers.size(); i++) {
				
		 		String center = (String)centers.get(i);    				

				Connection conn = connect(center);	
				String name = AppProperties.getStringProperty(center+".name").trim();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(selectDateSQL);
				
				String dateStr = null;
				int d, m, y;
				d = m = y = 0;
				if (rs.next()) {
					java.sql.Date date = rs.getDate(1);
					dateStr = dateFormat.format(date);
					Calendar cal = new GregorianCalendar();
					cal.setTime(date);
					d = cal.get(Calendar.DAY_OF_MONTH);
					m = cal.get(Calendar.MONTH);
					y = cal.get(Calendar.YEAR);
				}
				rs.close();
				String noStr = null;
				int no = 0;
				rs = stmt.executeQuery(selectNoSQL);
				if (rs.next()) {
					noStr = rs.getString(1).trim();
					no = Integer.parseInt(noStr);
				}
				rs.close();
				stmt.close();
				conn.close();
		 				
		 				
				Calendar now = Calendar.getInstance();
				int hour = now.get(Calendar.HOUR_OF_DAY);
				
				boolean ok = false;
				
				    				
				if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && 
					 (now.get(Calendar.YEAR) == y)) {
					if (((hour >= 8) && (hour < 11)) && (no == 1))
						ok = true;
					
					else if (((hour >= 11) && (hour < 15)) && (no == 2))
						ok = true;
					
					else if (((hour >= 15) && (hour < 17)) && (no == 3))
						ok = true;
					
					else if (((hour >= 17) || (hour < 8)) && (no == 4))
						ok = true;
				}
		 				  
		 		now.add(Calendar.DAY_OF_MONTH, -1);
		 		if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && (now.get(Calendar.YEAR) == y)) {
				 	if (hour < 8) 
				 		ok = true;
		 		}
		
		 		now.add(Calendar.DAY_OF_MONTH, -2);
				if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && (now.get(Calendar.YEAR) == y)) {
				 	if (hour < 8) 
				 		ok = true;
		 		}
		 				
				if (!ok)
					sendMail(dateFormat.format(now.getTime()), center, dateStr, noStr);
			}
		} catch (Exception e) {
			System.err.println(e);	
		}
	}


	private void sendMail(String nowStr, String center, String dateStr, String noStr) {
		String smtp = AppProperties.getStringProperty("smtp.host");
		boolean debug = AppProperties.getBooleanProperty("debug");
		String sender = AppProperties.getStringProperty("mail.sender");
		List receiverList = AppProperties.getStringArrayProperty("mail.receiver");
		
		try {
			MailSender ms = new MailSender(smtp, debug);				
			
			StringBuffer msgBuffer = new StringBuffer();
			msgBuffer.append("Error ExchangeRate Table");
			msgBuffer.append("\nCenter : " + center);
			msgBuffer.append("\nFixing Date : " + dateStr);
			msgBuffer.append("\nFixing No   : " + noStr);
					
			ms.sendMail(sender, receiverList, "ExchangeRate Check", msgBuffer.toString());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public Connection connect(String serviceCenter) throws SQLException {	
		String user = AppProperties.getStringProperty(serviceCenter+".db.user").trim();
		String pw = AppProperties.getStringProperty(serviceCenter+".db.password").trim();
		String dbURL = AppProperties.getStringProperty(serviceCenter+".db.url").trim();
		return(DriverManager.getConnection(dbURL, user, pw));
	}
	
	public static void main(String args[]) {
		new ExchangeRateCheck();
	}
}

    		
    		