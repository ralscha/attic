package gtf.check;

import java.io.*;
import java.util.*;
import java.text.*;

import common.util.*;


public class AWZACheck implements gtf.common.Constants {

	private Map bookingsMap = new TreeMap();
	private Map realMap = new TreeMap();
	private List specialList = new ArrayList();
	private List okList = new ArrayList();


	private DecimalFormat form;
	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
	private int oldestValueDate;
 
	public AWZACheck() {

		form = new DecimalFormat("#,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);

	}

	public void clear() {
		bookingsMap.clear();
		specialList.clear();
		okList.clear();
	}

	private Double makeDouble(String amount, String sign) {
		StringBuffer sb = new StringBuffer();
	
		if ("-".equals(sign))
			sb.append(sign);
	
		for (int i = 0; i < amount.length(); i++) {
			char tmp = amount.charAt(i);
			if (tmp != ' ') {
				sb.append(tmp);
			}			
		}			
		return (new Double(sb.toString()));					
	}
	
	private void deleteFilesStartWith(String path, String startName) {
		File dir = new File(path);
	 	File[] f = dir.listFiles();
	 	for (int i = 0; i < f.length; i++) {
	 		if (f[i].getName().startsWith(startName)) {
	 			f[i].delete();
	 		}
	 	}	
	}
	
	public void check() {
		String line;
		String macct = null;
		com.tce.math.TBigDecimal gtotal = com.tce.math.TBigDecimal.ZERO;
		
		try {
			String okFileName = AppProperties.getStringProperty(P_AWZA_CHECK_OK_FILE);
			
			File okFile = new File(okFileName);
			if (okFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(okFile));
				while ((line = br.readLine()) != null) {
					if (line.length() >= 26) {
						okList.add(line.substring(0,27));
					}
				}
				br.close();
			}
			
			
			String realBalanceFileName = AppProperties.getStringProperty(P_AZWA_CHECK_BALANCE_FILE);
			
			BufferedReader br = new BufferedReader(new FileReader(realBalanceFileName));
			while((line = br.readLine()) != null) {
				if (line.length() >= 47) {
					String curr = line.substring(16,19).trim();
					String amount = line.substring(20,40).trim();
					String sign = line.substring(40,41).trim();
					Double dou = makeDouble(amount, sign);
					realMap.put(curr, dou);	
				}				
			}
			br.close();
			
			String openItemsFileName = AppProperties.getStringProperty(P_AWZA_CHECK_DIFF_HTML_PREFIX);			
			String openItemsFilePath = AppProperties.getStringProperty(P_AWZA_CHECK_DIFF_HTML_PATH);
			String ovFile = AppProperties.getStringProperty(P_AWZA_CHECK_OVERVIEW_HTMLFILE);
			String okDate = AppProperties.getStringProperty(P_AWZA_CHECK_OK_DATE);
			String bookingsFile = AppProperties.getStringProperty(P_AWZA_CHECK_BOOKINGS_FILE);
	
			deleteFilesStartWith(openItemsFilePath,openItemsFileName);
	
			PrintWriter overpw = new PrintWriter(new FileWriter(ovFile));
			Calendar lastUpdate = Calendar.getInstance();
			printOverviewHeader(overpw, lastUpdate);
				
			BufferedReader dis = new BufferedReader(new FileReader(bookingsFile));
			while ((line = dis.readLine()) != null) {

				if (line.length() >= 109) {
					try {
	
						String acct = line.substring(0,19);
						
						Booking obj = new Booking();
						
						obj.setOrderNo(line.substring(20, 47));
						if (okDate.compareTo(obj.getOrderDate()) < 0) {
						
							String t = line.substring(49, 85).trim();
							
							int pos1 = t.indexOf("//");					
							int pos2 = t.indexOf("CAHA");
							if (pos2 == -1)
									pos2 = t.indexOf("CAHB");
							if (pos2 == -1)
									pos2 = t.indexOf("NFA");
              if (pos2 == -1)
                  pos2 = t.indexOf("HCKO");
							
							
							int pos3 = t.indexOf("/");
		
							if ((pos1 != -1) && (pos2 != -1)) {
								if (pos1 < pos2) 
									obj.setText(t.substring(pos2).trim());
								else
									obj.setText(t.substring(pos2, pos1).trim());
							}
							else if ((pos3 != -1) && (pos2 != -1)) {
								if (pos3 < pos2)
									obj.setText(t.substring(pos2).trim());
								else
									obj.setText(t.substring(pos2, pos3).trim());
							} 
							else if (pos2 != -1) {
								obj.setText(t.substring(pos2).trim());
							}
							else
								obj.setText(t);        
							
							obj.setValueDate(line.substring(104,112));
							obj.setAmount(new com.tce.math.TBigDecimal(line.substring(86, 103).trim()));		
		
							
							if (macct == null)
								macct = acct;
							else if (!macct.equals(acct)) {		
								
								String currency = macct.substring(macct.lastIndexOf('-')+1).trim();
										
								PrintWriter pw = new PrintWriter(new FileWriter(openItemsFilePath+openItemsFileName+currency+".html"));
								printHeader(pw, lastUpdate);	
								
								printTableHeader(pw, macct);
		
								oldestValueDate = Integer.MAX_VALUE;
								
								match(pw);
								printSpecialList(pw);
		
								printTotal(pw,gtotal);
								printTableFooter(pw);
		
								printOverviewRow(overpw, macct, openItemsFileName+currency+".html", gtotal, oldestValueDate, currency);
									
								specialList.clear();
								bookingsMap.clear();
								gtotal = com.tce.math.TBigDecimal.ZERO;
								
								macct = acct;
								printFooter(pw);						
								pw.close();
							}
							if (!okList.contains(obj.getOrderNo())) {
								gtotal = gtotal.add(obj.getAmount());
								if ( (obj.getText().indexOf("CAHA") == -1) && (obj.getText().indexOf("HT") == -1) &&
										(obj.getText().indexOf("CAHB") == -1) && (obj.getText().indexOf("NFA") == -1) &&
										(obj.getText().indexOf("CAHB") == -1) && (obj.getText().indexOf("HCKO") == -1) ) {
									specialList.add(obj);
								} else {
									List vl = (List) bookingsMap.get(obj.getText());
									if (vl != null) {
										vl.add(obj);
									} else {
										vl = new Vector();
										vl.add(obj);
										bookingsMap.put(obj.getText(), vl);
									}
								}
							}
						}
					} catch (NumberFormatException nfe) {
						System.out.println(nfe);
					}
				}
			}
			if (bookingsMap.size() > 0) {
				
				oldestValueDate = Integer.MAX_VALUE;

				String currency = macct.substring(macct.lastIndexOf('-')+1);
				PrintWriter pw = new PrintWriter(new FileWriter(openItemsFilePath+openItemsFileName+currency+".html"));
				printHeader(pw, lastUpdate);	
							
				printTableHeader(pw, macct);
				match(pw);
				printSpecialList(pw);
				printTotal(pw, gtotal);
				printTableFooter(pw);
				printFooter(pw);
				printOverviewRow(overpw, macct, openItemsFileName+currency+".html", gtotal, oldestValueDate, currency);
			
				pw.close();
			}
			
			
			dis.close();
			
			printOverviewFooter(overpw);		
			overpw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) throws Exception {
		new AWZACheck().check();
	}
	
	private void match(PrintWriter pw) {
		com.tce.math.TBigDecimal zero = com.tce.math.TBigDecimal.ZERO;
		 
		Iterator it = bookingsMap.values().iterator();
		Map newMap = new HashMap();
		Map newMap2 = new HashMap();
		
		while(it.hasNext()) {
			List vl = (List)it.next();
			com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
			for (int i = 0; i < vl.size(); i++) {
				Booking b = (Booking)vl.get(i);
				total = total.add(b.getAmount());                
			}
			if (!total.isZero()) {                    
				Booking speB = searchSpecialList(total.negate());   
				if (speB == null) {
					
					Map splitmap = split(vl);
										
					Iterator itm = splitmap.values().iterator();
					while(itm.hasNext()) {
						List sl = (List)itm.next();
						total = com.tce.math.TBigDecimal.ZERO;
						for (int i = 0; i < sl.size(); i++) {
							Booking b = (Booking)sl.get(i);
							total = total.add(b.getAmount());                
						}    
						if (!total.isZero()) {
							Booking sb = searchSpecialList(total.negate());
							
							if (sb == null) {
								Booking b = (Booking)sl.get(0);
								if (!searchSpecialList(total.negate(), b.getOrderDate())) {                              
									newMap.put(b.getKey(), sl);
								}
							}
						}
						
					}  
				}
			}
		}        
		
		Iterator itm = newMap.values().iterator();
		while(itm.hasNext()) {
			List sl = (List)itm.next();
			com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
			for (int i = 0; i < sl.size(); i++) {
				Booking b = (Booking)sl.get(i);
				total = total.add(b.getAmount());                
			}    
			if (!total.isZero()) {
				Booking sb = searchSpecialList(total.negate());
				
				if (sb == null) {
					Booking b = (Booking)sl.get(0);
					if (!searchSpecialList(total.negate(), b.getOrderDate())) {
						List hl = (List)newMap2.get(b.getText());
						if (hl != null) {
							hl.addAll(sl);
						} else {
							hl = new Vector();
							hl.addAll(sl);
							newMap2.put(b.getText(), hl);
						}
					}
				}
			}
			
		}  
				
		itm = newMap2.values().iterator();
		while(itm.hasNext()) {
			List sl = (List)itm.next();
			com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
			for (int i = 0; i < sl.size(); i++) {
				Booking b = (Booking)sl.get(i);
				total = total.add(b.getAmount());                
			}    
			if (!total.isZero()) {
				Booking sb = searchSpecialList(total.negate());
				
				if (sb == null) {
					Booking b = (Booking)sl.get(0);
					if (!searchSpecialList(total.negate(), b.getOrderDate()))                                
						printList(pw, sl);
				}
			}			
		}                    				
	}

	private void printBlankRow(PrintWriter pw) {
		pw.println("<tr>");
		pw.println("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
		pw.println("</tr>");
	}
	
	private void printFooter(PrintWriter pw) {
		pw.println("<p>");
		pw.println("<input type=\"Submit\" value=\"Add OK Items\">");
		pw.println("</form>");
		pw.println("</body>");
		pw.println("</html>");
	}
	
	private void printHeader(PrintWriter pw, Calendar lastUpdate) {
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>AWZA Intermediate Account Open Items</title>");
		pw.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
		pw.println("<link rel=stylesheet type=\"text/css\" href=\"AWZAOI.css\">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<a href=\"AWZAoverview.html\">Overview</a><br><br>");
		pw.println("<FONT SIZE=\"+1\"><b>AWZA Intermediate Account Open Items</b></FONT>");
	
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
		pw.print(lastUpdateFormat.format(lastUpdate.getTime()));
		
		pw.println("</font>");
			
		pw.println("<FORM ACTION=\""+AppProperties.getStringProperty(P_AWZA_CHECK_SERVLET)+
								"\" METHOD=\"POST\">");
		
	}
	
	private void printList(PrintWriter pw, List vl) {
		Iterator it = vl.iterator();
		com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
		
		while (it.hasNext()) {
			Booking bs = (Booking) it.next();
			total = total.add(bs.getAmount());
			pw.println("<tr>");
			
			pw.print("<td>");
			pw.print(bs.getOrderNo());
			pw.println("</td>");
	
			pw.print("<td>");
			pw.print(bs.getText());
			pw.println("</td>");
	
			pw.print("<td>");
			pw.print(bs.getValueDate());
			pw.println("</td>");
	
			setOldestValueDate(Integer.parseInt(bs.getValueDate()));
			
			pw.print("<td align=\"RIGHT\">");
			pw.print(form.format(bs.getAmount().doubleValue()));
			pw.println("</td>");
	
			pw.print("<td><input type=\"Checkbox\" name=\"");
			pw.print(bs.getOrderNo());
			pw.println("\"></td>");
			
			if (it.hasNext()) {	
				pw.print("<td>&nbsp;</td>");
			} else {
				pw.print("<td align=\"RIGHT\"><b>");
				pw.print(form.format(total.doubleValue()));
				pw.print("</b></td>");
			}
	
			pw.println("</tr>");
			
		}
		printBlankRow(pw);
	
	}

	private void printOverviewFooter(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
	}

	private void printOverviewHeader(PrintWriter pw, Calendar lastUpdate) {
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>AWZA Intermediate Account Open Items Overview</title>");
		pw.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<a href=\"index.html\" target=\"_top\">Home</a><br><br>");		
		pw.println("<FONT SIZE=\"+1\"><b>AWZA Intermediate Account Open Items</b></FONT>");
	
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
		pw.print(lastUpdateFormat.format(lastUpdate.getTime()));
		
		pw.println("</font>");
	
		pw.println("<HR>");
	
		pw.println("<h2>Overview</h2>");
	
		pw.println("<table width=\"80%\" border=\"0\" cellspacing=\"3\" cellpadding=\"3\">");
		pw.println("<tr>");
		pw.println("    <th align=\"LEFT\">account</th>");
		pw.println("    <th align=\"RIGHT\">difference</th>");
		pw.println("    <th align=\"RIGHT\">oldest value date</th>");
		pw.println("    <th align=\"RIGHT\">real difference</th>");		
		pw.println("</tr>");
	}

	private void printOverviewRow(PrintWriter pw, String account, String link, 
								com.tce.math.TBigDecimal diff, int valueDate, String currency) {
		pw.println("<tr>");
		pw.print("<td><a href=\"");
		pw.print(link);
		pw.print("\">");
		pw.print(account);
		pw.println("</a></td>");
		pw.print("<td align=\"RIGHT\">");
		pw.print(form.format(diff.doubleValue()));
		pw.println("</td>");
		pw.print("<td align=\"RIGHT\">");
		if (valueDate < Integer.MAX_VALUE)									
			pw.print(valueDate);
		else
			pw.print("&nbsp;");
		pw.println("</td>");
		pw.print("<td align=\"RIGHT\">");
		pw.print(form.format(((Double)realMap.get(currency)).doubleValue()));
		pw.println("</td>");
		pw.println("</tr>");

	}

	private void printSpecialList(PrintWriter pw) {

		com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
		
		if (specialList.size() > 0) {
		
			//Check Total
			Iterator it = specialList.iterator();
			while(it.hasNext()) {
				Booking bs = (Booking)it.next();
				total = total.add(bs.getAmount());
			}
			if (total.isZero()) 
				return;
			
			
			total = com.tce.math.TBigDecimal.ZERO;

			it = specialList.iterator();
			while (it.hasNext()) {
				pw.println("<tr>");
				Booking bs = (Booking)it.next();
				total = total.add(bs.getAmount());
				
				pw.print("<td>");
				pw.print(bs.getOrderNo());
				pw.println("</td>");
	
				pw.print("<td>");
				pw.print(bs.getText());
				pw.println("</td>");
	
				pw.print("<td>");
				pw.print(bs.getValueDate());
				pw.println("</td>");
	
				setOldestValueDate(Integer.parseInt(bs.getValueDate()));
	
				pw.print("<td align=\"RIGHT\">");
				pw.print(form.format(bs.getAmount().doubleValue()));
				pw.println("</td>");
	
				pw.print("<td><input type=\"Checkbox\" name=\"");
				pw.print(bs.getOrderNo());
				pw.println("\"></td>");
				
				if (it.hasNext()) {	
					pw.print("<td>&nbsp;</td>");
				} else {
					pw.print("<td align=\"RIGHT\"><b>");
					pw.print(form.format(total.doubleValue()));
					pw.print("</b></td>");
				}
				pw.println("</tr>");
				
			}
			printBlankRow(pw);
	
		}
		
	}

	private void printTableFooter(PrintWriter pw) {
		pw.println("</table>");
	}

	private void printTableHeader(PrintWriter pw, String macct) {
		pw.println("<hr>");
		pw.println("<h2>");
		pw.println(macct);
		pw.println("</h2>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("	<th>OrderNo</th>");
		pw.println("	<th>Text</th>");
		pw.println("	<th>ValueDate</th>");
		pw.println("	<th>Amount</th>");
		pw.println("	<th>OK</th>");
		pw.println("	<th><b>Difference</b></th>");
		pw.println("</tr>");
	}

	private void printTotal(PrintWriter pw, com.tce.math.TBigDecimal total) {
		pw.println("<tr>");
		pw.println("<td align=\"RIGHT\" colspan=\"5\"><b>TOTAL</b></td>");
		pw.println("<td align=\"RIGHT\"><b>");
		pw.println(form.format(total.doubleValue()));
		pw.println("</b></td>");
		pw.println("</tr>");
	
	}

	private Booking searchSpecialList(com.tce.math.TBigDecimal amount) {
		for (int i = 0; i < specialList.size(); i++) {
			Booking b = (Booking) specialList.get(i);
			if (b.getAmount().compareTo(amount) == 0) {
				specialList.remove(i);
				return b;
			}
		}
		return null;
	}

	private boolean searchSpecialList(com.tce.math.TBigDecimal amount, String orderDate) {
		List l = new Vector();
		com.tce.math.TBigDecimal total = com.tce.math.TBigDecimal.ZERO;
		for (int i = 0; i < specialList.size(); i++) {
			Booking b = (Booking) specialList.get(i);
			if (b.getOrderDate().equals(orderDate)) {
				l.add(b);
				total = total.add(b.getAmount());
			}
		}
		if (total.compareTo(amount) == 0) {
			specialList.removeAll(l);
			return true;
		}
		return false;
	}

	private void setOldestValueDate(int date) {
		if (date < oldestValueDate) {
			oldestValueDate = date;
		}
	}

	private Map split(List list) {
		Map m = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			Booking b = (Booking) list.get(i);
			List vl = (List) m.get(b.getKey());
			if (vl != null) {
				vl.add(b);
			} else {
				vl = new Vector();
				vl.add(b);
				m.put(b.getKey(), vl);
			}
		}
		return m;
	}
}